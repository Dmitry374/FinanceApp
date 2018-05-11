package com.example.financeapp.di.component

import com.example.financeapp.App
import com.example.financeapp.StorageActivity
import com.example.financeapp.di.builder.ActivityBuilder
import com.example.financeapp.di.modules.ContextModule
import com.example.financeapp.di.modules.NavigationModule
import com.example.financeapp.di.modules.ShowImageModule
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by Dima on 10.05.2018.
 */

@Component(modules = arrayOf(AndroidInjectionModule::class,
        NavigationModule::class,
        ShowImageModule::class,
        ContextModule::class,
        ActivityBuilder::class))
@Singleton
interface AppComponent {

    fun inject(app: App)

}