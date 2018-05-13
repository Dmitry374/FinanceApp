package com.example.financeapp.ui.records

import android.os.Bundle
import android.app.Fragment
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.financeapp.R
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import javax.inject.Inject

class FragmentRecords : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    lateinit var imgTest: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_records, container, false)

        imgTest = view.findViewById(R.id.imgTest)

        picasso
                .load(R.mipmap.ic_launcher)
                .into(imgTest)

        // Inflate the layout for this fragment
        return view
    }

    override fun onDetach() {
        super.onDetach()
    }
}