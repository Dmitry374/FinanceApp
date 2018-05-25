package com.example.financeapp.ui.sync

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.ui.navigation.NavigationActivity
import com.google.android.gms.common.api.GoogleApiClient

class SyncActivity : GoogleApiClientBaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        syncActivityViewModel.loadDataFromGoogleApiToDB(mGoogleApiClient)

        handler.postDelayed({

            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)

            finish()

        }, 1000)


    }
}
