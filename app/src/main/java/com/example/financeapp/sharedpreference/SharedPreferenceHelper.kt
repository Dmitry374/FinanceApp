package com.example.financeapp.sharedpreference

import android.content.SharedPreferences
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.IS_SIGN

/**
 * Класс для работы с SharedPreference
 */

class SharedPreferenceHelper(private val mSharedPreferences: SharedPreferences) {

    fun clearIsSignValue() = mSharedPreferences.edit().putBoolean(IS_SIGN, false).apply()
    fun setSignInAccount(isSign: Boolean) = mSharedPreferences.edit().putBoolean(IS_SIGN, isSign).apply()
    fun getSignInAccount(): Boolean = mSharedPreferences.getBoolean(IS_SIGN, false)
}