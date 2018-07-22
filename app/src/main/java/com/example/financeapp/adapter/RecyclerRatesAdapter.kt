package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.financeapp.R
import com.example.financeapp.network.Model
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class RecyclerRatesAdapter(private var mCtx: Context, private val picasso: Picasso, private var list: ArrayList<Model.Valuta>,
                           private val currency: Model.Currency,
                           private var listener: RecyclerRatesAdapter.OnItemClickListener)
    : RecyclerView.Adapter<RecyclerRatesAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(valute: Model.Valuta, position: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val valute = Math.rint(10000.0 * (list[position].Value.toDouble())) / 10000.0
        val previous = Math.rint(10000.0 * (list[position].Previous.toDouble())) / 10000.0

        val difference: Double = Math.rint(10000.0 * (valute - previous)) / 10000.0


        holder!!.tvExchange.text = "${list[position].Nominal} ${list[position].CharCode} = ${list[position].Value} руб."
        holder.tvCurrencyName.text = list[position].Name
        holder.tvCurrencyDifference.text = difference.toString()
        if (difference >= 0){
            holder.tvCurrencyDifference.setTextColor(ContextCompat.getColor(mCtx, R.color.color_income))
            picasso
                    .load(R.mipmap.ic_dp)
                    .fit() // will explain later
                    .into(holder.imgTypeDifference)
        } else {
            holder.tvCurrencyDifference.setTextColor(ContextCompat.getColor(mCtx, R.color.color_consumption))
            picasso
                    .load(R.mipmap.ic_dn)
                    .fit() // will explain later
                    .into(holder.imgTypeDifference)
        }
        holder.tvDateExchange.text = convertToNewFormat(currency.Date)

        holder.bind(list[position], listener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_resycler_exchange_rates, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvExchange = itemView.findViewById<TextView>(R.id.tvExchange)!!
        val tvCurrencyName = itemView.findViewById<TextView>(R.id.tvCurrencyName)
        val imgTypeDifference = itemView.findViewById<ImageView>(R.id.imgTypeDifference)
        val tvCurrencyDifference = itemView.findViewById<TextView>(R.id.tvCurrencyDifference)
        val tvDateExchange = itemView.findViewById<TextView>(R.id.tvDateExchange)

        fun bind(valuta: Model.Valuta, listener: OnItemClickListener, position: Int) {
            itemView.setOnClickListener { listener.onItemClick(valuta, position) }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToNewFormat(dateStr: String): String {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ")  // Принимаем дату
        val destFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")  // Конвертируем в
        sourceFormat.setTimeZone(utc)
        val convertedDate = sourceFormat.parse(dateStr)
        return destFormat.format(convertedDate)

    }


}