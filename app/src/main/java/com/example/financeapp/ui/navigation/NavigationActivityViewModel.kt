package com.example.financeapp.ui.navigation

import android.content.Context
import android.widget.Toast
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.R
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient

class NavigationActivityViewModel(private val context: Context,
                                  private val dbHelper: DBHelper, private val commonMethod: CommonMethod,
                                  private val sharedPreferenceHelper: SharedPreferenceHelper) {

//    Log Out From Account
    fun logOut(mGoogleApiClient: GoogleApiClient){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { status ->
            if (status.isSuccess) {
                commonMethod.goLogInScreen(sharedPreferenceHelper)
            } else {
                Toast.makeText(context, context.resources.getText(R.string.try_later), Toast.LENGTH_SHORT).show()
            }
        }
    }

//    Get User Data
    fun getUserData() = dbHelper.getInfAboutUser()

//    Delete User Data
    fun deleteUserData() = dbHelper.deleteInfAbutUser()
}