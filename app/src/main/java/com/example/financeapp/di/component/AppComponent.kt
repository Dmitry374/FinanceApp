package com.example.financeapp.di.component

import android.content.ContentValues
import android.os.Handler
import com.example.financeapp.App
import com.example.financeapp.CommonMethod
import com.example.financeapp.DBHelper
import com.example.financeapp.di.builder.ActivityBuilder
import com.example.financeapp.di.modules.*
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Component(modules = arrayOf(AndroidInjectionModule::class,
        ActivityBuilder::class,
        ShowImageModule::class,
        HandlerModule::class,
        FragmentModule::class,
        DBHelperModule::class,
        SharedPreferenceModule::class,
        RXJavaModule::class,
        CommonMethodModule::class,
        ContextModule::class))
@Singleton
interface AppComponent {

//    fun getContext(): Context
    fun getPicasso(): Picasso
    fun getHandler(): Handler
    fun getMainFragment(): FragmentMain
    fun getRecordsFragment(): FragmentRecords
    fun getDBHelper(): DBHelper
    fun getContentValues(): ContentValues
    fun getSharedPreferenceHelper(): SharedPreferenceHelper
    fun getCompositeDisposable(): CompositeDisposable
    fun getCommonMethodModule(): CommonMethod


    fun inject(app: App)

}