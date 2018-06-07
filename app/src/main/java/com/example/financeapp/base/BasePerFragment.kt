package com.example.financeapp.base

import android.os.Bundle
import com.example.financeapp.App
import com.example.financeapp.di.component.DaggerBasePerFragmentComponent
import com.example.financeapp.ui.registration.FragmentSignInViewModel
import javax.inject.Inject

abstract class BasePerFragment : BaseFragment() {

    @Inject
    lateinit var fragmentSignInViewModel: FragmentSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val basePerFragmentComponent = DaggerBasePerFragmentComponent
                .builder()
                .appComponent(App.appComponent)
                .build()

        basePerFragmentComponent.inject(this)
    }
}