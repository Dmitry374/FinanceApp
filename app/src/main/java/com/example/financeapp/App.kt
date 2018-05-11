package com.example.financeapp

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.financeapp.di.component.DaggerAppComponent
import com.example.financeapp.di.modules.ContextModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext

        DaggerAppComponent.builder()
//                .contextModule(ContextModule(mContext))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}