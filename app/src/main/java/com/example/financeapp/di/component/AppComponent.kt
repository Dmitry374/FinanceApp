package com.example.financeapp.di.component

import com.example.financeapp.App
import com.example.financeapp.di.builder.ActivityBuilder
import com.example.financeapp.di.modules.ContextModule
import com.example.financeapp.di.modules.FragmentModule
import com.example.financeapp.di.modules.HandlerModule
import com.example.financeapp.di.modules.ShowImageModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = arrayOf(AndroidInjectionModule::class,
        ActivityBuilder::class,
        ShowImageModule::class,
        HandlerModule::class,
        FragmentModule::class,
        ContextModule::class))
@Singleton
interface AppComponent {

    fun inject(app: App)

}