package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.financeapp.R
import com.example.financeapp.network.Model

class ListBillsAdapter(private val context: Context, private val list: ArrayList<Model.Bill>) : BaseAdapter() {

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

        val view: View? = LayoutInflater.from(context).inflate(R.layout.item_list_bill, parent, false)

        val tvNameBillFromList = view!!.findViewById<TextView>(R.id.tvNameBillFromList)
        tvNameBillFromList.text = list[position].name

        return view
    }

}