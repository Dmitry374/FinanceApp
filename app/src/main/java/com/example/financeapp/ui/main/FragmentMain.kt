package com.example.financeapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.GridBillAdapter
import com.example.financeapp.adapter.RecyclerRecordsAdapter
import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.common.Constants.Companion.UPDATE_ROW
import com.example.financeapp.network.Model
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.DecimalFormat


class FragmentMain : BasePerFragment() {

    private lateinit var listBills: ArrayList<Model.Bill>
    private lateinit var listRecords: ArrayList<Model.Record>

    private var amountOnBill: Double = 0.0

    private lateinit var decimalFormat: DecimalFormat // Формат отображения

    private var colorPosition = -1

    private lateinit var nameSelectedBill: String

//    Позиция выбранного счета (цветного)
    private var remindBillPosition = 0

    var balance: Double = 0.0

    private lateinit var linearLayoutManager: LinearLayoutManager

    val MENU_BALANCE = 1
    val MENU_LAST_RECORDS = 2

    var limitRecords = 0
    var limitRecordsPosition = 0

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

//        ------------------ Popup Menu -----------------------

        val popupMenuBalance: PopupMenu = PopupMenu(activity, settingsCardBalance)
        popupMenuBalance.menuInflater.inflate(R.menu.edit_balance_text, popupMenuBalance.menu)

        popupMenuBalance.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.editEditBalanceText -> {
                    val intent = Intent(activity, EditBalanceTextActivity::class.java)
                    intent.putExtra("titleBalance", fragmentMainViewModel.getTitleBalance())
                    startActivity(intent)
                }
            }

            true
        })

        settingsCardBalance.setOnClickListener {
            popupMenuBalance.show()
        }




        val popupMenuLastRecords: PopupMenu = PopupMenu(activity, settingsCardLastRecords)
        popupMenuLastRecords.menuInflater.inflate(R.menu.edit_last_records_text, popupMenuLastRecords.menu)

        popupMenuLastRecords.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.editEditLastRecordsText -> {
                    val intent = Intent(activity, EditLastRecordsTextActivity::class.java)
                    intent.putExtra("titleLastRecords", fragmentMainViewModel.getTitleLastRecord())
                    intent.putExtra("positionList", limitRecordsPosition)
                    startActivity(intent)
                }
            }

            true
        })

        settingsCardLastRecords.setOnClickListener {
            popupMenuLastRecords.show()
        }


//        -----------------------------------------------------

//        val navView = activity.findViewById<NavigationView>(R.id.nav_view)
//
////        Кнопка "Еще" - Переход ко всем записям на фрагмент "Записи"
//        btnShowMoreRecords.setOnClickListener {
//            val transaction = fragmentManager.beginTransaction()
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack if needed
//            transaction.replace(R.id.frame, recordsFragment)
//            transaction.addToBackStack(null)
//
//            transaction.commit()
//
//            activity.title = "Title"
//
//            navView.menu.getItem(1).setChecked(true)  // Делаем активной меню "Записи"
//        }

//        sPrefHelper.setRemindSelectedBill(0)
        fragmentMainViewModel.setRemindSelectedBill(0)

        listRecords = ArrayList<Model.Record>()

        updateGrid()

        fabMain.setOnClickListener({
            if (getBillCount() == 0){
                Toast.makeText(activity, getText(R.string.text_list_bills_empty), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("colorPosition", colorPosition)
                intent.putExtra("nameSelectedBill", nameSelectedBill)
                startActivity(intent)
            }
        })

        imgBtnSettingsBill.setOnClickListener {
            if (getBillCount() == 0){
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

        if (getBillCount() == 0){
            listBills = ArrayList<Model.Bill>()
            listRecords = ArrayList<Model.Record>()
            tvBalanceMain.text = "0 руб."
        } else {
            listBills = fragmentMainViewModel.getBillsFromDB()
//            Задаем позицию цвета по умолчанию, если в бд есть данные
            colorPosition = listBills[0].color_position

//            Имя выбранного счета
            try {
                nameSelectedBill = listBills[getRemindSelectedBill()].name
            } catch (e: java.lang.IndexOutOfBoundsException){  // Если счет на ктором стоишь будет удален
                fragmentMainViewModel.setRemindSelectedBill(0)
                nameSelectedBill = listBills[getRemindSelectedBill()].name
            }


            showTextEmptyRecords()  // Отображение надписи "Нет записей" по счету

//            Список записей по счету
            updateRecycler(nameSelectedBill)

//            Баланс счетов
            balance = fragmentMainViewModel.getBillBalance()
            tvBalanceMain.text = "${decimalFormat.format(balance)}."
        }

        listBills.add(Model.Bill("", "", "", 0, 0, 1,0))

        remindBillPosition = getRemindSelectedBill() // Позиция выбранного счета (цветного)

        val gridAdapter = GridBillAdapter(activity, picasso, remindBillPosition, listBills)

        gridBillMain.adapter = gridAdapter

        try {
            amountOnBill = listBills[getRemindSelectedBill()].amount.toDouble()
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
                colorPosition = listBills[position].color_position
//                Имя выбранного счета
                nameSelectedBill = listBills[position].name


                showTextEmptyRecords()  // Отображение надписи "Нет записей" по счету


                fragmentMainViewModel.setRemindSelectedBill(position) // Запоминаем позицию выбранного счета

                amountOnBill = listBills[position].amount.toDouble()
                amountOnBill = Math.rint(100.0 * amountOnBill)/100.0
                tvBalanceCard.text = "${decimalFormat.format(amountOnBill)}."

//                Обновить список записей
                updateRecycler(nameSelectedBill)

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateRecycler(nameBill: String){

        listRecords.clear()
//        Отображение всех записей
//        listRecords = fragmentMainViewModel.getRecordsFromDBByBill(nameBill)

//        Отображение последних записей в обратном порядке
        listRecords = fragmentMainViewModel.getLastRecordInReverseOrder(nameBill, limitRecords)
        tvInfoKolRecords.text = "${getText(R.string.text_last)} $limitRecords ${getText(R.string.text_word_record)}"

//        for (element in listRecords) {
//            Log.d("myLogs", "name = ${element.name}  bill =  ${element.bill}  sumOp = ${element.sumOp} " +
//                    "color = ${element.color}")
//        }

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerLastRecord.layoutManager = linearLayoutManager

        recyclerLastRecord.isNestedScrollingEnabled = false

        recyclerLastRecord.adapter = RecyclerRecordsAdapter(activity, listRecords, object : RecyclerRecordsAdapter.OnItemClickListener {
            override fun onItemClick(item: Model.Record, position: Int) {

                val intent = Intent(activity, AddRecordActivity::class.java)
                intent.putExtra("type", UPDATE_ROW)
                intent.putExtra("id", listRecords[position].id)
//                startActivityForResult(intent, REQUEST_CODE_UPDATE_RECORD)
                startActivity(intent)

            }
        })

    }

    private fun showTextEmptyRecords(){

//                Отображение надписи "Нет записей" по счету
        if (fragmentMainViewModel.getRecordsCountByBill(nameSelectedBill) == 0){
            no_records_text.visibility = View.VISIBLE
            recyclerLastRecord.visibility = View.GONE
            linAddition.visibility = View.GONE
        } else {
            no_records_text.visibility = View.GONE
            recyclerLastRecord.visibility = View.VISIBLE
            linAddition.visibility = View.VISIBLE
        }

    }

    private fun setTextTitleCard(){
        tvBalanceTextCard.text = fragmentMainViewModel.getTitleBalance()
        tvTitleLastRecords.text = fragmentMainViewModel.getTitleLastRecord()
        limitRecords = fragmentMainViewModel.getRecordLimit()
        limitRecordsPosition = fragmentMainViewModel.getRecordLimitPosition()
    }

    private fun getBillCount(): Int{
        return fragmentMainViewModel.getBillCount()
    }

    private fun getRemindSelectedBill(): Int {
        return fragmentMainViewModel.getRemindSelectedBill()
    }

    override fun onResume() {
        super.onResume()
        setTextTitleCard()
        updateGrid()
    }

}