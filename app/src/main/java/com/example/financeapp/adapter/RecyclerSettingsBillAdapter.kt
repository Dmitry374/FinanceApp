package com.example.financeapp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.network.Model
import com.squareup.picasso.Picasso

class RecyclerSettingsBillAdapter(private var mCtx: Context, private var list: List<Model.Bill>,
                                  private var listener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerSettingsBillAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(item: Model.Bill, position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvTextLink!!.text = list[position].name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.relMarkerBill.backgroundTintList = ColorStateList.valueOf(list[position].color)
        }

        holder.bind(list[position], listener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_recycler_settings_bill, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val imgImageLink = itemView.findViewById<ImageView>(R.id.imgMarkerBill)!!
        val relMarkerBill = itemView.findViewById<RelativeLayout>(R.id.relMarkerBill)!!
        val tvTextLink = itemView.findViewById<TextView>(R.id.tvNameBillSettings)!!

        fun bind(link: Model.Bill, listener: OnItemClickListener, position: Int) {
            itemView.setOnClickListener { listener.onItemClick(link, position) }
        }
    }


}