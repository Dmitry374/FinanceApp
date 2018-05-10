package com.example.financeapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Dima on 10.05.2018.
 */

@Module
class ContextModule(private val appContext: Context) {

    @Provides
    fun appContext(): Context = appContext;
}