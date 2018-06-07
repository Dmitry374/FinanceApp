package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.financeapp.R
import com.example.financeapp.network.Model
import com.example.financeapp.ui.custom.CircleTransform
import com.squareup.picasso.Picasso

class ListCategoryAdapter (private val context: Context, private val list: ArrayList<Model.Category>)
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

        val view: View? = LayoutInflater.from(context).inflate(R.layout.item_list_category, parent, false)

        val imgCategory = view!!.findViewById<ImageView>(R.id.imgCategory)

        val tvNameCategory = view.findViewById<TextView>(R.id.tvNameCategory)
        tvNameCategory.text = list[position].name

        Glide.with(context).load(list[position].icon)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(CircleTransform(context))
//                .override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .dontAnimate()
                .into(imgCategory)

        return view
    }

}