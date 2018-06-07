package com.example.financeapp.di.modules

import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.example.financeapp.ui.registration.FragmentRegistration
import com.example.financeapp.ui.registration.FragmentSignIn
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {
    @Provides
    @Singleton
    fun provideMainFragment() = FragmentMain()

    @Provides
    @Singleton
    fun providesRecordsFragment() = FragmentRecords()


    @Provides
    @Singleton
    fun providesFragmentRegistration() = FragmentRegistration()

    @Provides
    @Singleton
    fun providesFragmentSignIn() = FragmentSignIn()
}