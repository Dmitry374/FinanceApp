package com.example.financeapp.di.component

import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.di.modules.GoogleApiModule
import com.example.financeapp.di.scopes.PerActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseActivityModule::class, GoogleApiModule::class))
@PerActivity
interface GoogleApiClientComponent {

    fun inject(googleApiClientBaseActivity: GoogleApiClientBaseActivity)

}