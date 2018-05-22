package com.example.financeapp

import android.os.Bundle
import android.os.Handler
import com.example.financeapp.di.component.DaggerGoogleApiClientComponent
import com.example.financeapp.di.modules.GoogleApiModule
import com.example.financeapp.ui.main.FragmentMain
import com.example.financeapp.ui.records.FragmentRecords
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import javax.inject.Inject

abstract class GoogleApiClientBaseActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleApiClientComponent = DaggerGoogleApiClientComponent
                .builder()
                .appComponent(App.appComponent)
                .baseActivityModule(baseActivityModule)
//                .googleApiModule(GoogleApiModule(this, this))
                .googleApiModule(GoogleApiModule(this))
                .build()

        googleApiClientComponent.inject(this)

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        println("onConnectionFailed ->: " + connectionResult.errorMessage!!)
    }
}