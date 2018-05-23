package com.example.financeapp.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class RXJavaModule {
    @Singleton
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}