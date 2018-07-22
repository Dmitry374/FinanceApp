package com.example.financeapp.ui.start

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.ui.navigation.NavigationActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : GoogleApiClientBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.activity_start)
        window.setBackgroundDrawableResource(R.drawable.background)

        if (startActivityViewModel.getUserCountFromDB() == 0){
            sPrefHelper.clearIsSignValue()
        }

        picasso
                .load(R.drawable.logo)
                .into(imgLogoText)

        handler.postDelayed({

//            Если вход выполнен, переходим сразу в приложение
            if (sPrefHelper.getSignInAccount()){
                goNavigationScreen()
            } else {
//                Иначе авторизируемся
                commonMethod.goLogInScreen(sPrefHelper)
            }
        }, 3000)

    }

    private fun goNavigationScreen() {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}
