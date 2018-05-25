package com.example.financeapp.ui.authorisation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.common.CommonMethod
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient


class AuthorisationActivityViewModel(private val context: Context, private val commonMethod: CommonMethod) {

    fun googleApiClientRequest(mGoogleApiClient: GoogleApiClient) : Intent{
        return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
    }

    fun getGoogleApiClientResponse(data: Intent){
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        handleSignInResult(result)
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            commonMethod.goMainScreen()
        } else {
            Toast.makeText(context, context.resources.getText(R.string.try_again), Toast.LENGTH_SHORT).show()
        }

    }
}