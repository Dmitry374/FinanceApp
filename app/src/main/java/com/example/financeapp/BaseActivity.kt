package com.example.financeapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.financeapp.di.component.BaseActivityComponent
import com.example.financeapp.di.component.DaggerBaseActivityComponent
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var handler: Handler

    @Inject
    lateinit var mainFragment: FragmentMain

    @Inject
    lateinit var recordsFragment: FragmentRecords

    private val baseActivityComponent: BaseActivityComponent by lazy {
        DaggerBaseActivityComponent
                .builder()
                .appComponent(App.appComponent)
//                .baseActivityModule(BaseActivityModule(this))
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun initDependencies() {
        injectComponent(baseActivityComponent)
    }

    protected open fun injectComponent(baseActivityComponent: BaseActivityComponent) {
        baseActivityComponent.inject(this)
    }

    protected val baseActivityModule: BaseActivityModule
        get() = BaseActivityModule(this)
}