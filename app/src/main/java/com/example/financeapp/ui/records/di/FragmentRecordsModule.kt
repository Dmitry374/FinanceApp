package com.example.financeapp.ui.records.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.records.FragmentRecordsViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentRecordsModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, sharedPreferenceHelper: SharedPreferenceHelper)
            = FragmentRecordsViewModel(dbHelper, sharedPreferenceHelper)
}