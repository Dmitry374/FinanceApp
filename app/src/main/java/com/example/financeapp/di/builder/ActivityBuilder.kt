package com.example.financeapp.di.builder

import com.example.financeapp.AutorisationActivity
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.example.financeapp.ui.navigation.NavigationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
//    @ContributesAndroidInjector
//    abstract fun bindNavigationActivity(): NavigationActivity

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): FragmentMain

    @ContributesAndroidInjector
    abstract fun bindRecordsFragment(): FragmentRecords
}