package com.example.financeapp.ui.authorisation.di

import android.content.Context
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.ui.authorisation.AuthorisationActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthorisationActivityModule (private val context: Context) {
    @PerActivity
    @Provides
    fun provideViewModel(commonMethod: CommonMethod) =
            AuthorisationActivityViewModel(context, commonMethod)
}