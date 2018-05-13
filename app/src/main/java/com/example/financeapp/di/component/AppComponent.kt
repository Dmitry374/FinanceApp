package com.example.financeapp.di.component

import com.example.financeapp.App
import com.example.financeapp.di.modules.ContextModule
import com.example.financeapp.di.modules.HandlerModule
import com.example.financeapp.di.modules.ShowImageModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Dima on 10.05.2018.
 */

@Component(modules = arrayOf(AndroidInjectionModule::class,
        ContextModule::class,
        HandlerModule::class,
        ShowImageModule::class))
@Singleton
interface AppComponent {

    fun inject(app: App)

}