package com.example.financeapp.base

import android.os.Bundle
import com.example.financeapp.App
import com.example.financeapp.di.component.DaggerBasePerFragmentComponent
import com.example.financeapp.ui.main.FragmentMainViewModel
import com.example.financeapp.ui.records.FragmentRecordsViewModel
import javax.inject.Inject

abstract class BasePerFragment : BaseFragment() {

    @Inject
    lateinit var fragmentMainViewModel: FragmentMainViewModel

    @Inject
    lateinit var fragmentRecordViewModel: FragmentRecordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val basePerFragmentComponent = DaggerBasePerFragmentComponent
                .builder()
                .appComponent(App.appComponent)
                .build()

        basePerFragmentComponent.inject(this)
    }
}