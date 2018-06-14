package com.example.financeapp.ui.sync.di

import android.content.Context
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.sync.SyncActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class SyncActivityModule(private val context: Context) {
    @PerActivity
    @Provides
    fun provideViewModel(networkHelper: NetworkHelper,
                         commonMethod: CommonMethod, sharedPreferenceHelper: SharedPreferenceHelper): SyncActivityViewModel {
        return SyncActivityViewModel(context, networkHelper, commonMethod, sharedPreferenceHelper)
    }
}