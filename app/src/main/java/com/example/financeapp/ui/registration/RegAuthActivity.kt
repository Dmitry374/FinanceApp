package com.example.financeapp.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.financeapp.R
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.ui.authorisation.AuthorisationActivity
import kotlinx.android.synthetic.main.activity_reg_auth.*

class RegAuthActivity : BaseActivity() {

    private lateinit var typeAction: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_auth)
        setSupportActionBar(toolbarRegAuth)
        window.setBackgroundDrawableResource(R.drawable.background)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        typeAction = intent.getStringExtra("typeAction")

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out)

        if (typeAction == "registration"){
            fragmentManager.beginTransaction().replace(R.id.containerAuthRegistration, registrationFragment).commit()
            fragmentTransaction.commitAllowingStateLoss()
        } else {
            fragmentManager.beginTransaction().replace(R.id.containerAuthRegistration, signInFragment).commit()
            fragmentTransaction.commitAllowingStateLoss()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, AuthorisationActivity::class.java)
        startActivity(intent)

        finish()
    }
}
