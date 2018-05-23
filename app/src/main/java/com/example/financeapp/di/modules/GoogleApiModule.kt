package com.example.financeapp.di.modules

import com.example.financeapp.base.BaseActivity
import com.example.financeapp.di.scopes.PerActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides

/*
*
* @Module
class GoogleApiModule(
        private val mConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener,
        private val mConnectionCallbacks: GoogleApiClient.ConnectionCallbacks) {
*
* */

@Module
class GoogleApiModule(
        private val mConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener) {
    @PerActivity
    @Provides
    fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
    }

    @PerActivity
    @Provides
    fun providesGoogleApiClient(baseActivity: BaseActivity,
                                googleSignInOptions: GoogleSignInOptions): GoogleApiClient {
        return GoogleApiClient.Builder(baseActivity)
                .enableAutoManage(baseActivity, mConnectionFailedListener)
//                .addConnectionCallbacks(mConnectionCallbacks)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }
}