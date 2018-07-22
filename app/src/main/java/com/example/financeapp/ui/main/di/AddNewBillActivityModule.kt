package com.example.financeapp.ui.main.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.ui.main.AddNewBillActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class AddNewBillActivityModule {

    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, networkHelper: NetworkHelper)
            = AddNewBillActivityViewModel(dbHelper, networkHelper)
}