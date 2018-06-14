package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.financeapp.R


class SpinnerBillsAllRecordsAdapter(private val context: Context, private val list: List<String>)
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

        val view: View? = LayoutInflater.from(context).inflate(R.layout.spinner_item_all_records, parent, false)

        val tvSpinnerBillName = view!!.findViewById<TextView>(R.id.tvSpinnerBillName)
        tvSpinnerBillName.text = list[position]

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val view: View? = LayoutInflater.from(context).inflate(R.layout.spinner_item_all_records, parent, false)
        val tvSpinnerBillName = view!!.findViewById<TextView>(R.id.tvSpinnerBillName)
        tvSpinnerBillName.text = list[position]

        view.setBackgroundColor(ContextCompat.getColor(context, R.color.color_spinner_dropdown))

        tvSpinnerBillName.gravity = Gravity.CENTER
        tvSpinnerBillName.setTextColor(ContextCompat.getColor(context, R.color.color_text_spinner_dropdown))
        return view
    }

}