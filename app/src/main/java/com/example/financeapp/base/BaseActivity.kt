package com.example.financeapp.base

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.financeapp.App
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.db.DBHelper
import com.example.financeapp.di.component.BaseActivityComponent
import com.example.financeapp.di.component.DaggerBaseActivityComponent
import com.example.financeapp.di.modules.BaseActivityModule
import com.example.financeapp.network.Api
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.example.financeapp.ui.registration.FragmentRegistration
import com.example.financeapp.ui.registration.FragmentSignIn
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var handler: Handler

    @Inject
    lateinit var mainFragment: FragmentMain

    @Inject
    lateinit var recordsFragment: FragmentRecords

    @Inject
    lateinit var registrationFragment: FragmentRegistration

    @Inject
    lateinit var signInFragment: FragmentSignIn

    @Inject
    lateinit var dbHelper: DBHelper

    @Inject
    lateinit var contentValues: ContentValues

    @Inject
    lateinit var sPrefHelper: SharedPreferenceHelper

//    @Inject
//    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var commonMethod: CommonMethod

//    @Inject
//    lateinit var api: Api

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var picasso: Picasso

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


//    override fun onDestroy() {
//        super.onDestroy()
//        compositeDisposable.clear()
//        compositeDisposable.dispose()
//
//    }
}