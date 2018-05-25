package com.example.financeapp.common

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.authorisation.AuthorisationActivity
import com.example.financeapp.ui.sync.SyncActivity

/*
* Класс для общих методов
* */

class CommonMethod(private val context: Context) {

//    Метод возвращения на экран авторизации
    fun goLogInScreen(sPrefHelper: SharedPreferenceHelper) {
//        Запись в SPref статус: Выход из аккаунта
        sPrefHelper.setSignInAccount(false)

        val intent = Intent(context, AuthorisationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

//    Переход на экран синхронизации
    fun goMainScreen() {
        val intent = Intent(context, SyncActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}