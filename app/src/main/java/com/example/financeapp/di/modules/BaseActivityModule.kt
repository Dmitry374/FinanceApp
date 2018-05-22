package com.example.financeapp.di.modules

import com.example.financeapp.BaseActivity
import com.example.financeapp.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class BaseActivityModule(private val mBaseActivity: BaseActivity) {
    @PerActivity
    @Provides
    fun providesBaseActivity(): BaseActivity {
        return mBaseActivity
    }

}