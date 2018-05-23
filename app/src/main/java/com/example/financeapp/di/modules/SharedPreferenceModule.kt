package com.example.financeapp.di.modules

import android.content.Context
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль для SharedPreferences
 */

@Module
class SharedPreferenceModule(private val mContext: Context) {

    companion object {
        const val SHARED_PREFERENCE_NAME = "finance_shared_preference"
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper() = SharedPreferenceHelper(mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE))
}