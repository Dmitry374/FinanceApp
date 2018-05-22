package com.example.financeapp.di.component

import com.example.financeapp.BaseActivity
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.di.scopes.PerActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseActivityModule::class))
@PerActivity
interface BaseActivityComponent {

    fun inject(baseActivity: BaseActivity)

}