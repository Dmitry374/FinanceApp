package com.example.financeapp.base

import android.os.Bundle
import com.example.financeapp.App
import com.example.financeapp.di.component.DaggerGoogleApiClientComponent
import com.example.financeapp.di.modules.GoogleApiModule
import com.example.financeapp.ui.navigation.NavigationActivityViewModel
import com.example.financeapp.ui.navigation.di.NavigationActivityModule
import com.example.financeapp.ui.sync.SyncActivityViewModel
import com.example.financeapp.ui.sync.di.SyncActivityModule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import javax.inject.Inject

abstract class GoogleApiClientBaseActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var mGoogleApiClient: GoogleApiClient

    @Inject
    lateinit var navigationActivityViewModel: NavigationActivityViewModel

    @Inject
    lateinit var syncActivityViewModel: SyncActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleApiClientComponent = DaggerGoogleApiClientComponent
                .builder()
                .appComponent(App.appComponent)
                .baseActivityModule(baseActivityModule)
//                .googleApiModule(GoogleApiModule(this, this))
                .googleApiModule(GoogleApiModule(this))
                .navigationActivityModule(NavigationActivityModule(this))
                .syncActivityModule(SyncActivityModule())
                .build()

        googleApiClientComponent.inject(this)

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        println("onConnectionFailed ->: " + connectionResult.errorMessage!!)
    }
}