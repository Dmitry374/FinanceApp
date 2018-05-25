package com.example.financeapp.ui.sync.di

import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.sync.SyncActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class SyncActivityModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, commonMethod: CommonMethod, sharedPreferenceHelper: SharedPreferenceHelper): SyncActivityViewModel {
        return SyncActivityViewModel(dbHelper, commonMethod, sharedPreferenceHelper)
    }
}