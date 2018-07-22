package com.example.financeapp.di.modules

import android.content.Context
import com.example.financeapp.common.CommonMethod
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonMethodModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideCommonMethod() = CommonMethod(context)
}