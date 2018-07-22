package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.financeapp.R
import com.example.financeapp.network.Model
import com.example.financeapp.ui.custom.CircleTransform

class RecyclerRecordsAdapter(private var mCtx: Context, private var list: List<Model.Record>,
                             private var listener: RecyclerRecordsAdapter.OnItemClickListener)
    : RecyclerView.Adapter<RecyclerRecordsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Model.Record, position: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        Glide.with(mCtx).load(list[position].icon)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(CircleTransform(mCtx))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .dontAnimate()
                .into(holder!!.imgCategoryRecycler)

        holder.tvNameCategoryRecycler.text = list[position].name
        holder.tvNameBillRecycler.text = list[position].bill

        holder.tvSumOperation.setTextColor(list[position].color)

        if (list[position].type == mCtx.resources.getText(R.string.button_add_record_consumption)){  // Если тип - расход
            holder.tvSumOperation.text = "- ${list[position].sum} руб."
        } else {
            holder.tvSumOperation.text = "${list[position].sum} руб."
        }
        holder.tvDateOperation.text = list[position].date

        holder.bind(list[position], listener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_resycler_record, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imgCategoryRecycler = itemView.findViewById<ImageView>(R.id.imgCategoryRecycler)!!
        val tvNameCategoryRecycler = itemView.findViewById<TextView>(R.id.tvNameCategoryRecycler)!!
        val tvNameBillRecycler = itemView.findViewById<TextView>(R.id.tvNameBillRecycler)!!
        val tvSumOperation = itemView.findViewById<TextView>(R.id.tvSumOperation)!!
        val tvDateOperation = itemView.findViewById<TextView>(R.id.tvDateOperation)!!

        fun bind(record: Model.Record, listener: OnItemClickListener, position: Int) {
            itemView.setOnClickListener { listener.onItemClick(record, position) }
        }
    }


}