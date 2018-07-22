package com.example.financeapp.ui.main.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.main.FragmentMainViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentMainModule {

    @PerActivity
    @Provides
    fun provideViewModule(dbHelper: DBHelper, sPrefHelper: SharedPreferenceHelper,
                          networkHelper: NetworkHelper) = FragmentMainViewModel(dbHelper, sPrefHelper, networkHelper)

}