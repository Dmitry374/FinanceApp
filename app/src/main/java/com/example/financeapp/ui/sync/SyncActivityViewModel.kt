package com.example.financeapp.ui.sync

import android.util.Log
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult

class SyncActivityViewModel(private val networkHelper: NetworkHelper, private val commonMethod: CommonMethod,
                            private val sharedPreferenceHelper: SharedPreferenceHelper) {

    lateinit var name: String
    lateinit var surname: String

    fun loadDataFromGoogleApiToDB(mGoogleApiClient: GoogleApiClient){

        val opr: OptionalPendingResult<GoogleSignInResult> = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback {
                googleSignInResult -> handleSignInResult(googleSignInResult)
            }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {

            Log.d("muLogs", "SyncActivityViewModel handleSignInResult")

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

            loadUserDataOnServer(name, surname, account.email!!,
                    photoUrl, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)

        } else {
            commonMethod.goLogInScreen(sharedPreferenceHelper)
        }
    }

    fun loadUserDataOnServer(name: String, surname: String, email: String, photoUrl: String,
                             password: String, gender: String, dateOfBirth: String){
//        Рекурсивный метод записи данных на сервер (т.к. данные не записываютс с первого раза)
        networkHelper.loadUserDataOnServer(name, surname, email, photoUrl, password, gender, dateOfBirth)
    }

    fun closeDisposable() = networkHelper.closeDisposable()

}