package com.example.financeapp.di.component

import com.example.financeapp.AutorisationActivity
import com.example.financeapp.GoogleApiClientBaseActivity
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.di.modules.GoogleApiModule
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.ui.navigation.NavigationActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseActivityModule::class, GoogleApiModule::class))
@PerActivity
interface GoogleApiClientComponent {

    fun inject(googleApiClientBaseActivity: GoogleApiClientBaseActivity)

}