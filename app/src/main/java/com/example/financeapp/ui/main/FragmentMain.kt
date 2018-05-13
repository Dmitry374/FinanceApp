package com.example.financeapp.ui.main

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.financeapp.R
import dagger.android.AndroidInjection

class FragmentMain : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onDetach() {
        super.onDetach()
    }

}