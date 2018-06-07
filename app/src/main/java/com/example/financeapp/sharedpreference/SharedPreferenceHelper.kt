package com.example.financeapp.sharedpreference

import android.content.SharedPreferences
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.IS_SIGN
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.REMIND_BILL_POSITION
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.USER_EMAIL
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.USER_NAME
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.USER_PHOTO_URL
import com.example.financeapp.sharedpreference.SharedPreferenceConstants.Companion.USER_SURNAME

/**
 * Класс для работы с SharedPreference
 */

class SharedPreferenceHelper(private val mSharedPreferences: SharedPreferences) {

    fun clearIsSignValue() = mSharedPreferences.edit().putBoolean(IS_SIGN, false).apply()
    fun setSignInAccount(isSign: Boolean) = mSharedPreferences.edit().putBoolean(IS_SIGN, isSign).apply()
    fun getSignInAccount(): Boolean = mSharedPreferences.getBoolean(IS_SIGN, false)

    fun setRemindSelectedBill(billPosition: Int) = mSharedPreferences.edit().putInt(REMIND_BILL_POSITION, billPosition).apply()
    fun getRemindSelectedBill(): Int = mSharedPreferences.getInt(REMIND_BILL_POSITION, 0)

    fun setUserName(name: String) = mSharedPreferences.edit().putString(USER_NAME, name).apply()
    fun getUserName(): String = mSharedPreferences.getString(USER_NAME, "")

    fun setUserSurname(surname: String) = mSharedPreferences.edit().putString(USER_SURNAME, surname).apply()
    fun getUserSurname(): String = mSharedPreferences.getString(USER_SURNAME, "")

    fun setUserEmail(email: String) = mSharedPreferences.edit().putString(USER_EMAIL, email).apply()
    fun getUserEmail(): String = mSharedPreferences.getString(USER_EMAIL, "")

    fun setUserPhotoUrl(photoUrl: String) = mSharedPreferences.edit().putString(USER_PHOTO_URL, photoUrl).apply()
    fun getUserPhotoUrl(): String = mSharedPreferences.getString(USER_PHOTO_URL, "")
}