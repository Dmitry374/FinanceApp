package com.example.financeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.financeapp.R
import com.example.financeapp.network.Model
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class GridBillAdapter(private val context: Context, private val picasso: Picasso,
                      private val coloredItem: Int, private val list: ArrayList<Model.Bill>) : BaseAdapter() {

    var amountOnBill: Double = 0.0

    lateinit var decimalFormat: DecimalFormat

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflator.inflate(R.layout.item_grid_main, null)

        val cardMain: CardView = view.findViewById(R.id.card_main)
        val linTexts: LinearLayout = view.findViewById(R.id.linTexts)
        val linImageAdd: RelativeLayout = view.findViewById(R.id.linImageAdd)
        val tvItemGridNameCount: TextView = view.findViewById(R.id.tvItemGridNameCount)
        val imgView: ImageView = view.findViewById(R.id.imgGridAdd)
        val tvItemGridAmount: TextView = view.findViewById(R.id.tvItemGridAmount)

//        data class Bill(var name: String, var amount: String, var currency: String, var color: String, var synchronise: Int)



        if (list[position].name == "" && list[position].amount == "" &&
                list[position].currency == "" && list[position].color == 0){
            linImageAdd.visibility = View.VISIBLE
            picasso
                    .load(R.mipmap.ic_add_bill)
                    .fit() // will explain later
                    .into(imgView)

//            tvItemGridNameCount.visibility = View.GONE
//            tvItemGridAmount.visibility = View.GONE
            linTexts.visibility = View.GONE
        } else {
            if (position == coloredItem){
                cardMain.setCardBackgroundColor(list[coloredItem].color)
            } else {
                cardMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.not_selected_card))
            }

            tvItemGridNameCount.text = list[position].name

            amountOnBill = Math.rint(100.0 * (list[position].amount.toDouble()))/100.0
            decimalFormat = DecimalFormat("#,###.## руб")

//            tvItemGridAmount.text = "${list[position].amount} руб."
            tvItemGridAmount.text = "${decimalFormat.format(amountOnBill)}."
            linImageAdd.visibility = View.GONE
        }

        return view
    }

}