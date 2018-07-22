package com.example.financeapp.di.component

import android.content.ContentValues
import android.os.Handler
import com.example.financeapp.App
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.builder.ActivityBuilder
import com.example.financeapp.di.modules.*
import com.example.financeapp.network.Api
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.creditcalculator.FragmentCreditCalculator
import com.example.financeapp.ui.exchangerates.FragmentExchangeRates
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.example.financeapp.ui.registration.FragmentRegistration
import com.example.financeapp.ui.registration.FragmentSignIn
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = arrayOf(AndroidInjectionModule::class,
        ActivityBuilder::class,
        ShowImageModule::class,
        HandlerModule::class,
        FragmentModule::class,
        DBHelperModule::class,
        SharedPreferenceModule::class,
        CommonMethodModule::class,
        NetworkModule::class,
        ContextModule::class))
@Singleton
interface AppComponent {

//    fun getContext(): Context
    fun getPicasso(): Picasso
    fun getHandler(): Handler
    fun getMainFragment(): FragmentMain
    fun getRecordsFragment(): FragmentRecords
    fun getFragmentCreditCalculator(): FragmentCreditCalculator
    fun getFragmentExchangeRates(): FragmentExchangeRates
    fun getDBHelper(): DBHelper
    fun getContentValues(): ContentValues
    fun getSharedPreferenceHelper(): SharedPreferenceHelper
    fun getCommonMethodModule(): CommonMethod
    fun getServerApi(): Api
    fun getNetworkHelper(): NetworkHelper
    fun getFragmentRegistration(): FragmentRegistration
    fun getFragmentSignIn(): FragmentSignIn


    fun inject(app: App)

}