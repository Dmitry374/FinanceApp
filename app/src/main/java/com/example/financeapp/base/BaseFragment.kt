package com.example.financeapp.base

import android.app.Fragment
import android.os.Bundle
import com.example.financeapp.App
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.component.BaseFragmentComponent
import com.example.financeapp.di.component.DaggerBaseFragmentComponent
import com.example.financeapp.di.modules.BaseFragmentModule
import com.example.financeapp.network.Api
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.registration.FragmentRegistration
import com.example.financeapp.ui.registration.FragmentSignIn
import com.squareup.picasso.Picasso
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var dbHelper: DBHelper

    @Inject
    lateinit var sPrefHelper: SharedPreferenceHelper

    @Inject
    lateinit var commonMethod: CommonMethod

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var registrationFragment: FragmentRegistration

    @Inject
    lateinit var signInFragment: FragmentSignIn

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