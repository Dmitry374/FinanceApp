package com.example.financeapp.ui.sync

import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.db.DBHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult

class SyncActivityViewModel(private val dbHelper: DBHelper, private val commonMethod: CommonMethod,
                            private val sharedPreferenceHelper: SharedPreferenceHelper) {

    fun loadDataFromGoogleApiToDB(mGoogleApiClient: GoogleApiClient){

        val opr: OptionalPendingResult<GoogleSignInResult> = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
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

            val photoUrl = if (account!!.photoUrl == null){
                EMPTY_STRING
            } else {
                account.photoUrl.toString()
            }

            dbHelper.insertInfAboutUser(account.givenName!!, account.familyName!!, account.email!!, photoUrl,
                    EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, 0)

        } else {
            commonMethod.goLogInScreen(sharedPreferenceHelper)
        }
    }
}