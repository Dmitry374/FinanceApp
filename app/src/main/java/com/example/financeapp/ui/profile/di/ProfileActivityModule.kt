package com.example.financeapp.ui.profile.di

import android.content.Context
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.profile.ProfileActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class ProfileActivityModule(private val context: Context) {

    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, commonMethod: CommonMethod,
                         sharedPreferenceHelper: SharedPreferenceHelper): ProfileActivityViewModel {
        return ProfileActivityViewModel(context, dbHelper, commonMethod, sharedPreferenceHelper)
    }
}