package com.example.financeapp.ui.navigation.di

import com.example.financeapp.CommonMethod
import com.example.financeapp.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.navigation.NavigationActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class NavigationActivityModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, commonMethod: CommonMethod, sharedPreferenceHelper: SharedPreferenceHelper) =
            NavigationActivityViewModel(dbHelper, commonMethod, sharedPreferenceHelper)
}