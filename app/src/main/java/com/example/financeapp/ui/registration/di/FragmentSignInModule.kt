package com.example.financeapp.ui.registration.di

import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.ui.registration.FragmentSignInViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentSignInModule {
    @PerActivity
    @Provides
    fun provideViewModel(dbHelper: DBHelper) = FragmentSignInViewModel(dbHelper)
}