package com.example.financeapp.common

import android.content.Context
import android.content.Intent
import com.example.financeapp.common.Constants.Companion.REGISTER_EMAIL
import com.example.financeapp.common.Constants.Companion.REGISTER_PASSWORD
import com.example.financeapp.common.Constants.Companion.TYPE_AUTHORISATION
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.authorisation.AuthorisationActivity
import com.example.financeapp.ui.navigation.NavigationActivity
import com.example.financeapp.ui.sync.SyncActivity

/*
* Класс для общих методов
* */

class CommonMethod(private val context: Context) {

//    Метод возвращения на экран авторизации
    fun goLogInScreen(sPrefHelper: SharedPreferenceHelper) {
//        Запись в SPref статус: Выход из аккаунта
        sPrefHelper.setSignInAccount(false)
        sPrefHelper.clearAllPrefs(context)
//    sPrefHelper.setUserEmail(EMPTY_STRING)

        val intent = Intent(context, AuthorisationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

//    Переход на экран синхронизации
    fun goSyncScreen(typeRegAuth: String, email: String, password: String) {
        val intent = Intent(context, SyncActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(TYPE_AUTHORISATION, typeRegAuth)
        intent.putExtra(REGISTER_EMAIL, email)
        intent.putExtra(REGISTER_PASSWORD, password)
        context.startActivity(intent)
    }

//    Переход на Экран Навигации
    fun goNavigationScreen() {
        val intent = Intent(context, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

//    fun onTextChangedListener(editText: EditText): TextWatcher {
//        return object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                editText.removeTextChangedListener(this)
//
//                try {
//                    var originalString = s.toString()
//
//                    val longval: Long?
//                    if (originalString.contains(",")) {
//                        originalString = originalString.replace(",".toRegex(), "")
//                    }
//                    longval = java.lang.Long.parseLong(originalString)
//
//                    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
//                    formatter.applyPattern("#,###.##")
//                    val formattedString = formatter.format(longval)
//
//                    //setting text after format to EditText
//                    editText.setText(formattedString)
//                    editText.setSelection(editText.text.toString().length)
//                } catch (nfe: NumberFormatException) {
//                    nfe.printStackTrace()
//                }
//
//                editText.addTextChangedListener(this)
//            }
//        }
//    }




}