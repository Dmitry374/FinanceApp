package com.example.financeapp.di.component

import com.example.financeapp.base.BaseFragment
import com.example.financeapp.di.modules.BaseFragmentModule
import com.example.financeapp.di.scopes.PerActivity
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseFragmentModule::class))
@PerActivity
interface BaseFragmentComponent {

    fun inject(baseFragment: BaseFragment)

}