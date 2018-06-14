package com.example.financeapp.di.component

import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.di.modules.BaseFragmentModule
import com.example.financeapp.di.scopes.PerActivity
import com.example.financeapp.ui.main.di.FragmentMainModule
import com.example.financeapp.ui.records.di.FragmentRecordsModule
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(BaseFragmentModule::class,
                FragmentMainModule::class,
                FragmentRecordsModule::class))
@PerActivity
interface BasePerFragmentComponent {

    fun inject(basePerFragment: BasePerFragment)

}