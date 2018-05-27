package com.example.financeapp.ui.start

import android.content.Intent
import android.os.Bundle
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.ui.navigation.NavigationActivity

class StartActivity : GoogleApiClientBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (startActivityViewModel.getUserCountFromDB() == 0){
            sPrefHelper.clearIsSignValue()
        }

        handler.postDelayed({

//            Если вход выполнен, переходим сразу в приложение
            if (sPrefHelper.getSignInAccount()){
                goNavigationScreen()
            } else {
//                Иначе авторизируемся
                commonMethod.goLogInScreen(sPrefHelper)
            }
        }, 300)

    }

    private fun goNavigationScreen() {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}
