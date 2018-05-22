package com.example.financeapp

import android.app.Fragment
import android.os.Bundle
import com.example.financeapp.di.component.BaseFragmentComponent
import com.example.financeapp.di.component.DaggerBaseFragmentComponent
import com.example.financeapp.di.modules.BaseFragmentModule
import com.squareup.picasso.Picasso
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private val baseFragmentComponent: BaseFragmentComponent by lazy {
        DaggerBaseFragmentComponent.builder()
                .appComponent(App.appComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun initDependencies() {
        injectComponent(baseFragmentComponent)
    }

    protected open fun injectComponent(baseActivityComponent: BaseFragmentComponent) {
        baseActivityComponent.inject(this)
    }

    protected val baseFragmentModule: BaseFragmentModule
        get() = BaseFragmentModule(this)
}