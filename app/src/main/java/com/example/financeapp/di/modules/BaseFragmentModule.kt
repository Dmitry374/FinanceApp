package com.example.financeapp.di.modules

import com.example.financeapp.BaseFragment
import com.example.financeapp.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class BaseFragmentModule(private val mBaseFragment: BaseFragment) {
    @PerActivity
    @Provides
    fun providesBaseFragment(): BaseFragment {
        return mBaseFragment
    }
}