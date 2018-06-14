package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.financeapp.R

class SpinnerKolRecordsAdapter(private val context: Context, private val list: IntArray)
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

        val view: View? = LayoutInflater.from(context).inflate(R.layout.spinner_kol_records, parent, false)

        val tvKolRecord = view!!.findViewById<TextView>(R.id.tvKolRecord)
        tvKolRecord.text = "${list[position]}"

        return view
    }

}