package com.example.financeapp.di.modules

import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
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
}