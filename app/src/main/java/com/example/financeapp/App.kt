package com.example.financeapp

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.financeapp.di.component.AppComponent
import com.example.financeapp.di.component.DaggerAppComponent
import com.example.financeapp.di.modules.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    companion object {
        lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

//    @Inject
//    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext

        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(mContext))
                .dBHelperModule(DBHelperModule(mContext))
                .sharedPreferenceModule(SharedPreferenceModule(mContext))
                .commonMethodModule(CommonMethodModule(mContext))
                .networkModule(NetworkModule(mContext))
                .build()

        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

//    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector
}