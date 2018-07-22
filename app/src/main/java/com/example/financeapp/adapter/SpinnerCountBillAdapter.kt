package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.financeapp.R

class SpinnerCountBillAdapter(private val context: Context, private val list: IntArray)
    : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        //        Используем созданные, но неиспользуемые view
//        var view: View? = convertView
//        if (view == null){
//            view = LayoutInflater.from(context).inflate(R.layout.spinner_count_bill, parent, false)
//        }

        val view: View? = LayoutInflater.from(context).inflate(R.layout.spinner_count_bill, parent, false)

        val cardView = view!!.findViewById<CardView>(R.id.card_spinner_count_bill)
        cardView.setCardBackgroundColor(list[position])

        return view
    }
}