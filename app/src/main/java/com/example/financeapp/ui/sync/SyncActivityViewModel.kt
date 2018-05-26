package com.example.financeapp.ui.sync

import android.annotation.SuppressLint
import android.util.Log
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Api
import com.example.financeapp.network.Model
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SyncActivityViewModel(private val dbHelper: DBHelper, private val api: Api, private val commonMethod: CommonMethod,
                            private val sharedPreferenceHelper: SharedPreferenceHelper) {

    private val compositeDisposable by lazy { CompositeDisposable() }

    lateinit var name: String
    lateinit var surname: String

    fun loadDataFromGoogleApiToDB(mGoogleApiClient: GoogleApiClient){

        val opr: OptionalPendingResult<GoogleSignInResult> = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {

            val account = result.signInAccount!!

//            Чтобы в БД не шли записи "null" и "null" (в name и surname)
            if (account.givenName == "null" || account.familyName == "null"){
                name = account.email!!
                surname = ""
            } else {
                name = account.givenName!!
                surname = account.familyName!!
            }

            val photoUrl = if (account.photoUrl == null){
                EMPTY_STRING
            } else {
                account.photoUrl.toString()
            }

//            Рекурсивный метод записи данных на сервер (т.к. данные не записываютс с первого раза)
            makeRequest(name, surname, account.email!!,
                    photoUrl, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)

        } else {
            commonMethod.goLogInScreen(sharedPreferenceHelper)
        }
    }

    private fun makeRequest(name: String, surname: String, email: String, photoUrl: String, password: String,
                            gender: String, dateOfBirth: String){

        compositeDisposable.add(
                api.registerUser(name, surname, email, photoUrl, password, gender, dateOfBirth)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                object : Consumer<List<Model.User>> {
                                    @SuppressLint("SetTextI18n")
                                    @Throws(Exception::class)
                                    override fun accept(userList: List<Model.User>) {
                                        Log.i("myLogs", "RxJava2: Response from server ...")
//                                        Log.d("myLogs", "FROM SERVER name = ${userList.get(0).name} surname = ${userList.get(0).surname} email = ${userList.get(0).email} photoUrl = ${userList.get(0).photourl}  dateOfBirth = ${userList.get(0).datebirth}")
                                        dbHelper.insertInfAboutUser(userList.get(0).name, userList.get(0).surname, userList.get(0).email, userList.get(0).photourl, userList.get(0).password, userList.get(0).gender, userList.get(0).datebirth, 1)

//                                        Вызов рекурсивного метода или на следующее активити
                                        if (dbHelper.getUserCount() == 0){
                                            makeRequest(name, surname, email, photoUrl, password, gender, dateOfBirth)
                                        } else {
                                            commonMethod.goNavigationScreen()
                                        }

                                    }
                                },
                                object : Consumer<Throwable> {
                                    @Throws(Exception::class)
                                    override fun accept(t: Throwable) {
                                        Log.i("myLogs", "RxJava2, HTTP Error: " + t.message)
                                    }
                                }
                        )
        )

    }

    fun closeCompositeDisposable(){
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}