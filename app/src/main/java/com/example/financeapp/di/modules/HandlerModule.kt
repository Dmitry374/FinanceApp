package com.example.financeapp.di.modules

import android.os.Handler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HandlerModule {
    @Singleton
    @Provides
    fun handler(): Handler {
        return Handler()
    }
}