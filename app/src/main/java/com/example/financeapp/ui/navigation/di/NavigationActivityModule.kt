package com.example.financeapp.ui.navigation.di

import android.content.Context
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.navigation.NavigationActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class NavigationActivityModule(private val context: Context) {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, commonMethod: CommonMethod,
                         sharedPreferenceHelper: SharedPreferenceHelper): NavigationActivityViewModel {
        return NavigationActivityViewModel(context, dbHelper, commonMethod, sharedPreferenceHelper)
    }
}