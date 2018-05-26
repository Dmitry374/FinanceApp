package com.example.financeapp.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/*
* Класс для работы с сетевыми запросами
* */

class NetworkHelper(private val context: Context,
                    private val api: Api,
                    private val dbHelper: DBHelper) {

//    fun registerUserOnServer(user: Model.User){
//
//        Log.d("myLogs", "registerUserOnServer")
//
////        compositeDisposable.add(
////                api.registerUser(user.name, user.surname, user.email, user.photoUrl, user.password,
////                        user.gender, user.dateOfBirth)
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribe({
////
////                            Log.d("myLogs", "registerUserOnServer disposable")
////
////                            dbHelper.insertInfAboutUser(it.name, it.surname, it.email, it.photoUrl,
////                                    it.password, it.gender, it.dateOfBirth, 1)
////
////                        }, {
////                            Log.d("myLogs", "registerUserOnServer Error")
////                            Toast.makeText(context, context.resources.getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
////                        })
////        )
//
////        compositeDisposable.add(
////                api.registerUser(user.name, user.surname, user.email, user.photoUrl, user.password,
////                        user.gender, user.dateOfBirth)
////                        .subscribeOn(AndroidSchedulers.mainThread())
////        )
//
////        val disposable = api.registerUser(user.name, user.surname, user.email, user.photoUrl, user.password,
////                user.gender, user.dateOfBirth)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe({
////
////                    Log.d("myLogs", "registerUserOnServer disposable")
////
////                    dbHelper.insertInfAboutUser(it.name, it.surname, it.email, it.photoUrl,
////                            it.password, it.gender, it.dateOfBirth, 1)
////
////                }, {
////                    Toast.makeText(context, context.resources.getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
////                })
//    }

}