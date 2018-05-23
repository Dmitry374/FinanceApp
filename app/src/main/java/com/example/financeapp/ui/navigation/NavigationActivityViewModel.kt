package com.example.financeapp.ui.navigation

import com.example.financeapp.CommonMethod
import com.example.financeapp.Constants.Companion.EMPTY_STRING
import com.example.financeapp.DBHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient

class NavigationActivityViewModel(private val dbHelper: DBHelper, private val commonMethod: CommonMethod,
                                  private val sharedPreferenceHelper: SharedPreferenceHelper) {

    fun loadDataFromGoogleApiToDB(mGoogleApiClient: GoogleApiClient){

        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount

            if (account != null) {
                dbHelper.insertInfAboutUser(account.givenName!!, account.familyName!!, account.email!!,
                        EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, 0)
            }

        } else {
            commonMethod.goLogInScreen(sharedPreferenceHelper)
        }
    }
}