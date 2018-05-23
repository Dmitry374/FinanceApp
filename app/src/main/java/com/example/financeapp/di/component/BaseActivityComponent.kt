package com.example.financeapp.di.component

import com.example.financeapp.base.BaseActivity
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.ui.navigation.di.NavigationActivityModule
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseActivityModule::class, NavigationActivityModule::class))
@PerActivity
interface BaseActivityComponent {

    fun inject(baseActivity: BaseActivity)

}