package com.example.financeapp.di.builder

import com.example.financeapp.NavigationActivity
import com.example.financeapp.NavigationActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(NavigationActivityModule::class))
    abstract fun bindNavigationActivity(): NavigationActivity
}