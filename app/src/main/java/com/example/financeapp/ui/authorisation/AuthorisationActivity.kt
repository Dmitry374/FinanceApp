package com.example.financeapp.ui.authorisation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.ui.navigation.NavigationActivity
import com.example.financeapp.ui.sync.SyncActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_authorisation.*

//class AuthorisationActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
class AuthorisationActivity : GoogleApiClientBaseActivity() {

    private val SIGN_IN_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorisation)

        signInButtonGoogle.setSize(SignInButton.SIZE_WIDE)
        signInButtonGoogle.setColorScheme(SignInButton.COLOR_DARK)

        signInButtonGoogle.setOnClickListener {
            val intent = authorisationActivityViewModule.googleApiClientRequest(mGoogleApiClient)
            startActivityForResult(intent, SIGN_IN_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            authorisationActivityViewModule.getGoogleApiClientResponse(data)
        }
    }

}
