package com.example.financeapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.GridBillAdapter
import com.example.financeapp.adapter.RecyclerRecordsAdapter
import com.example.financeapp.base.BaseFragment
import com.example.financeapp.network.Model
import kotlinx.android.synthetic.main.content_settings_bill.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.DecimalFormat
import com.example.financeapp.R.id.fab
import android.support.v4.widget.NestedScrollView



class FragmentMain : BaseFragment() {

    private lateinit var listBills: ArrayList<Model.Bill>
    private lateinit var listRecords: ArrayList<Model.Record>

    private var amountOnBill: Double = 0.0

    private lateinit var decimalFormat: DecimalFormat // Формат отображения

    private var colorPosition = -1

    lateinit var nameSelectedBill: String

//    Позиция выбранного счета (цветного)
    private var remindBillPosition = 0

    var balance: Double = 0.0

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Hide Fab on scrolling
        nsvMain.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                fabMain.hide()
            } else {
                fabMain.show()
            }
        })

        sPrefHelper.setRemindSelectedBill(0)

        listRecords = ArrayList<Model.Record>()

        updateGrid()

        fabMain.setOnClickListener({
            if (dbHelper.getBillCount() == 0){
                Toast.makeText(activity, getText(R.string.text_list_bills_empty), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("colorPosition", colorPosition)
                intent.putExtra("nameSelectedBill", nameSelectedBill)
                startActivity(intent)
            }
        })

        imgBtnSettingsBill.setOnClickListener {
            if (dbHelper.getBillCount() == 0){
                Toast.makeText(activity, getText(R.string.text_list_bills_empty), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, SettingsBillActivity::class.java)
                startActivity(intent)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateGrid(){

//        Формат отображения
        decimalFormat = DecimalFormat("#,###.## руб")

        if (dbHelper.getBillCount() == 0){
            listBills = ArrayList<Model.Bill>()
            listRecords = ArrayList<Model.Record>()
            tvBalanceMain.text = "0 руб."
        } else {
            listBills = dbHelper.getBillsFromDB()
//            Задаем позицию цвета по умолчанию, если в бд есть данные
            colorPosition = listBills[0].colorPosition
//            Имя выбранного счета
            nameSelectedBill = listBills[sPrefHelper.getRemindSelectedBill()].name

//            Список записей по счету
            updateRecycler(nameSelectedBill)

//            Баланс счетов
            balance = dbHelper.getBillBalance()
            tvBalanceMain.text = "${decimalFormat.format(balance)}."
        }

        listBills.add(Model.Bill("", "", "", 0, 0,0))

        remindBillPosition = sPrefHelper.getRemindSelectedBill() // Позиция выбранного счета (цветного)

        val gridAdapter = GridBillAdapter(activity, picasso, remindBillPosition, listBills)  // di

        gridBillMain.adapter = gridAdapter

        try {
            amountOnBill = listBills[sPrefHelper.getRemindSelectedBill()].amount.toDouble()
            amountOnBill = Math.rint(100.0 * amountOnBill)/100.0
            tvBalanceCard.text = "${decimalFormat.format(amountOnBill)}."
        } catch (e: java.lang.NumberFormatException){
            tvBalanceCard.text = "0 руб."
        }

        gridBillMain.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position == gridAdapter.count - 1) {

                val intent = Intent(activity, AddNewBillActivity::class.java)
                startActivity(intent)
            } else {

                gridBillMain.adapter = GridBillAdapter(activity, picasso, position, listBills)
//                Задаем позицию цвета по умолчанию
                colorPosition = listBills[position].colorPosition
//                Имя выбранного счета
                nameSelectedBill = listBills[position].name

                sPrefHelper.setRemindSelectedBill(position) // Запоминаем позицию выбранного счета

                amountOnBill = listBills[position].amount.toDouble()
                amountOnBill = Math.rint(100.0 * amountOnBill)/100.0
                tvBalanceCard.text = "${decimalFormat.format(amountOnBill)}."

//                Обновить список записей
                updateRecycler(nameSelectedBill)

            }
        }

    }

    private fun updateRecycler(nameBill: String){

        listRecords.clear()
        listRecords = dbHelper.getRecordsFromDB(nameBill)

//        for (element in listRecords) {
//            Log.d("myLogs", "name = ${element.name}  bill =  ${element.bill}  sumOp = ${element.sumOp} " +
//                    "color = ${element.color}")
//        }

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerLastRecord.layoutManager = linearLayoutManager

        recyclerLastRecord.isNestedScrollingEnabled = false

        recyclerLastRecord.adapter = RecyclerRecordsAdapter(activity, listRecords, object : RecyclerRecordsAdapter.OnItemClickListener {
            override fun onItemClick(item: Model.Record, position: Int) {
                Toast.makeText(activity, listRecords[position].name, Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        updateGrid()
    }

}