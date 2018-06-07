package com.example.financeapp.di.component

import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.di.modules.BaseFragmentModule
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.ui.registration.di.FragmentSignInModule
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseFragmentModule::class,
                FragmentSignInModule::class))
@PerActivity
interface BasePerFragmentComponent {

    fun inject(basePerFragment: BasePerFragment)

}