package com.example.financeapp.network

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.REGISTER_OWN
import com.example.financeapp.db.DBHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/*
* Класс для работы с сетевыми запросами
* */

class NetworkHelper(private val context: Context,
                    private val commonMethod: CommonMethod,
                    private val api: Api,
                    private val dbHelper: DBHelper) {

    lateinit var disposable: Disposable

    fun loadUserDataOnServer(name: String, surname: String, email: String, photoUrl: String, password: String,
                             gender: String, dateOfBirth: String){

        disposable = api.registerUser(name, surname, email, photoUrl, password, gender, dateOfBirth)
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

                                Log.d("myLogs", "NetworkHelper UserCount = ${dbHelper.getUserCount()}")

//                                        Вызов рекурсивного метода или на следующее активити
                                val count = dbHelper.getUserCount()
                                if (dbHelper.getUserCount() == 0){
                                    loadUserDataOnServer(name, surname, email, photoUrl, password, gender, dateOfBirth)
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

    }

//    fun checkUserByEmail(email: String){
//        disposable = api.checkUserExist(email)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        object : Consumer<String> {
//                            @SuppressLint("SetTextI18n")
//                            @Throws(Exception::class)
//                            override fun accept(response: String) {
//                                commonMethod.closeProgressDialog()
//                                Log.i("myLogs", "RxJava2: Response from server ...")
//
//                                if (response == "success"){
//                                    commonMethod.goSyncScreen(REGISTER_OWN)
//                                } else {
//                                    Toast.makeText(context, context.getText(R.string.text_user_already_exists), Toast.LENGTH_SHORT).show()
//                                }
//
//                            }
//                        },
//                        object : Consumer<Throwable> {
//                            @Throws(Exception::class)
//                            override fun accept(t: Throwable) {
//                                commonMethod.closeProgressDialog()
//                                Log.i("myLogs", "RxJava2, HTTP Error: " + t.message)
//                                Toast.makeText(context, context.getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                )
//    }

    fun closeDisposable(){
        disposable.dispose()

    }

}