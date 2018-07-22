package com.example.financeapp.ui.authorisation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.ui.registration.RegAuthActivity
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_authorisation.*

//class AuthorisationActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
class AuthorisationActivity : GoogleApiClientBaseActivity() {

    private val SIGN_IN_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.activity_authorisation)
        window.setBackgroundDrawableResource(R.drawable.background)

        signInButtonGoogle.setSize(SignInButton.SIZE_WIDE)
        signInButtonGoogle.setColorScheme(SignInButton.COLOR_LIGHT)

        picasso
                .load(R.drawable.logo)
                .into(imgLogoAuthorisation)

        signInButtonGoogle.setOnClickListener {
            val intent = authorisationActivityViewModule.googleApiClientRequest(mGoogleApiClient)
            startActivityForResult(intent, SIGN_IN_CODE)
        }

        btnRegisterAccount.setOnClickListener {
            val intent = Intent(this, RegAuthActivity::class.java)
            intent.putExtra("typeAction", "registration")
            startActivity(intent)
            finish()
        }

        btnSignInAccount.setOnClickListener {
            val intent = Intent(this, RegAuthActivity::class.java)
            intent.putExtra("typeAction", "signIn")
            startActivity(intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            authorisationActivityViewModule.getGoogleApiClientResponse(data)
        }
    }

}
