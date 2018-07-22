package com.example.financeapp.ui.main.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.ui.main.EditBillActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class EditBillActivityModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper, networkHelper: NetworkHelper)
            = EditBillActivityViewModel(dbHelper, networkHelper)
}