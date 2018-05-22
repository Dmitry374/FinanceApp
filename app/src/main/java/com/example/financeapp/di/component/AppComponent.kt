package com.example.financeapp.di.component

import android.app.Application
import android.content.Context
import android.os.Handler
import com.example.financeapp.App
import com.example.financeapp.AutorisationActivity
import com.example.financeapp.di.builder.ActivityBuilder
import com.example.financeapp.di.modules.*
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.squareup.picasso.Picasso
import dagger.BindsInstance
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

//    fun getContext(): Context
    fun getPicasso(): Picasso
    fun getHandler(): Handler
    fun getMainFragment(): FragmentMain
    fun getRecordsFragment(): FragmentRecords


    fun inject(app: App)

}