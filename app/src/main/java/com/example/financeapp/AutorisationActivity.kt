package com.example.financeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.financeapp.di.modules.GoogleApiModule
import com.example.financeapp.ui.navigation.NavigationActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_autorisation.*
import javax.inject.Inject

//class AutorisationActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
class AutorisationActivity : GoogleApiClientBaseActivity() {

    val SIGN_IN_CODE = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorisation)

        signInButtonGoogle.setSize(SignInButton.SIZE_WIDE)
        signInButtonGoogle.setColorScheme(SignInButton.COLOR_DARK)

        signInButtonGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(intent, SIGN_IN_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            goMainScreen()
        } else {
            Toast.makeText(this, "No account", Toast.LENGTH_SHORT).show()
        }

    }

    private fun goMainScreen() {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
