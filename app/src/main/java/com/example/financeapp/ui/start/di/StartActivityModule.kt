package com.example.financeapp.ui.start.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.start.StartActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class StartActivityModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper) = StartActivityViewModel(dbHelper)
}