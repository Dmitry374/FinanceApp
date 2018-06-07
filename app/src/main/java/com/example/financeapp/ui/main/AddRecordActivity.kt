package com.example.financeapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.SpinnerTypeRecord
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.network.Model

import kotlinx.android.synthetic.main.activity_add_record.*
import kotlinx.android.synthetic.main.content_add_record.*
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : BaseActivity() {

    private lateinit var listBills: ArrayList<Model.Bill>

    private val TAG = AddRecordActivity::class.java.simpleName

    private val REQUEST_CODE_LIST_BILL = 111
    private val REQUEST_CODE_LIST_CATEGORY = 333
    private val REQUEST_CODE_LIST_BILL_SECOND = 555

    lateinit var currentType: String

    lateinit var newAmountBill: String

    var categoryIcon: Int = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (dbHelper.getBillCount() == 0){
//            listBills = ArrayList<Model.Bill>()
//        } else {
//            listBills = dbHelper.getBillsFromDB()
//        }
//
//        val colorPosition = intent.getIntExtra("colorPosition", 0)
//
//        when(colorPosition){
//            in 0..2 -> setTheme(R.style.RedTheme)
//            in 3..5 -> setTheme(R.style.OrangeTheme)
//            in 6..8 -> setTheme(R.style.BrownTheme)
//            in 9..11 -> setTheme(R.style.YellowTheme)
//            in 12..14 -> setTheme(R.style.GreenTheme)
//            in 15..17 -> setTheme(R.style.BluelTheme)
//            in 18..20 -> setTheme(R.style.BlueTheme)
//            in 21..23 -> setTheme(R.style.PurpleTheme)
//            in 24..26 -> setTheme(R.style.PinkTheme)
//
//            else -> setTheme(R.style.AppTheme)
//        }

        setContentView(R.layout.activity_add_record)
        setSupportActionBar(toolbarAddRecord)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarAddRecord.setNavigationOnClickListener {
            finish()
        }

        val sdf = SimpleDateFormat("dd.MM.yyyy")

        currentType = resources.getText(R.string.button_add_record_income) as String

        setNameBill(intent)
        tvToBill.text = resources.getText(R.string.string_empty)

//        Массив тип записей
        val listRecords = resources.getStringArray(R.array.recordType)

//        Выствить знаки
        setSigns(listRecords, 0)
        Log.d("myLogs", "$TAG currentType = $currentType")

        spinnerTypeRecord.adapter = SpinnerTypeRecord(this, listRecords)

        spinnerTypeRecord.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                setSigns(listRecords, position)
            }

        }

        linBtnFirstParam.setOnClickListener {
            val intent = Intent(this, ListBillActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LIST_BILL)
        }

        linBtnSecondParam.setOnClickListener {
            secondParameterClick()
        }

//        Добавление новой записи
        btnAddNewRecord.setOnClickListener {
            Log.d("myLogs", "$TAG currentType = $currentType")

            if (edSumNewRecord.text.toString().trim() == ""){
                Toast.makeText(this, resources.getText(R.string.enter_sum), Toast.LENGTH_SHORT).show()
            } else {

//                ---------------------- Доход -------------------------

                if (currentType == resources.getText(R.string.button_add_record_income) as String){

//                    Add Record
                    if ((tvToBill.text as String).toLowerCase()
                            == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                        secondParameterClick()
                        Toast.makeText(this, resources.getText(R.string.text_select_category), Toast.LENGTH_SHORT).show()
                    } else {

//                        Add Bill
                        val amountBill = dbHelper.getBillSum(tvFromBill.text as String) + edSumNewRecord.text.toString().toDouble()
                        newAmountBill = amountBill.toString()

                        dbHelper.updateAmountBill(newAmountBill, tvFromBill.text as String)

//                        Add record
                        val currentDate = sdf.format(Date())

                        dbHelper.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
                                ContextCompat.getColor(this, R.color.color_income),
                                categoryIcon, currentDate, 0)

                        finish()
                    }


                }

//                ----------------------- Рассход ----------------------
                if (currentType == resources.getText(R.string.button_add_record_consumption) as String){

//                    Add Record
                    if ((tvToBill.text as String).toLowerCase()
                            == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                        secondParameterClick()
                        Toast.makeText(this, resources.getText(R.string.text_select_category), Toast.LENGTH_SHORT).show()
                    } else {

//                        Add Bill
                        val amountBill = dbHelper.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
                        newAmountBill = amountBill.toString()

                        dbHelper.updateAmountBill(newAmountBill, tvFromBill.text as String)

//                        Add record
                        val currentDate = sdf.format(Date())

                        dbHelper.addNewRecord(tvToBill.text.toString(), "- ${edSumNewRecord.text}",
                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_consumption).toString(),
                                ContextCompat.getColor(this, R.color.color_consumption),
                                categoryIcon, currentDate, 0)

                        finish()
                    }

                }

//                ------------------------ Перевод ----------------------
                if (currentType == resources.getText(R.string.button_add_record_transfer) as String){

                    if (tvToBill.text.toString().toLowerCase() == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                        Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
                    } else if (tvFromBill.text.toString() == tvToBill.text.toString()){
                        Toast.makeText(this, resources.getText(R.string.text_select_another_bill), Toast.LENGTH_SHORT).show()
                    } else {
//                        Add Bill
                        var amountBill = dbHelper.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
                        newAmountBill = amountBill.toString()

                        dbHelper.updateAmountBill(newAmountBill, tvFromBill.text as String)

                        amountBill = dbHelper.getBillSum(tvToBill.text as String) + edSumNewRecord.text.toString().toDouble()
                        newAmountBill = amountBill.toString()

                        dbHelper.updateAmountBill(newAmountBill, tvToBill.text as String)


                        if ((tvToBill.text as String).toLowerCase()
                                == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                            secondParameterClick()
                            Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
                        } else {

                            val currentDate = sdf.format(Date())

                            dbHelper.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                    tvFromBill.text.toString(), resources.getText(R.string.button_add_record_transfer).toString(),
                                    ContextCompat.getColor(this, R.color.color_transfer),
                                    R.drawable.transfer, currentDate, 0)

                            finish()
                        }
                    }

                }





            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_LIST_BILL) {
            if (data != null) {
                setNameBill(data)
            }
        }

        if (requestCode == REQUEST_CODE_LIST_CATEGORY){
            if (data != null) {
                setCategory(data)
            }
        }

        if (requestCode == REQUEST_CODE_LIST_BILL_SECOND) {
            if (data != null) {
                setSecondBill(data)
            }
        }
    }

    private fun secondParameterClick(){

        if (currentType == resources.getText(R.string.button_add_record_transfer) as String){
            val intent = Intent(this, ListBillActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LIST_BILL_SECOND)
        } else {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LIST_CATEGORY)
        }

    }

    private fun setNameBill(intent: Intent){
        tvFromBill.text = intent.getStringExtra("nameSelectedBill")
    }

    private fun setCategory(intent: Intent){
        tvToBill.text = intent.getStringExtra("nameSelectedCategory")
        categoryIcon = intent.getIntExtra("iconSelectedCategory", 0)
    }

    private fun setSecondBill(intent: Intent){
        tvToBill.text = intent.getStringExtra("nameSelectedSecondBill")
    }

    //    Выствить знаки
    fun setSigns(listRecords: Array<String>, position: Int){

        if (listRecords[position] == resources.getText(R.string.button_add_record_transfer)){  // position == "Перевод"
            imgArrowTransfer.visibility = View.VISIBLE
            tvSignEditBill.visibility = View.INVISIBLE
            tvFirstParam.text = resources.getText(R.string.param_one_from_bill_v1) // "Со счета"
            tvSecondParam.text = resources.getText(R.string.param_two_from_bill_v1) // "На счет"

            currentType = resources.getText(R.string.button_add_record_transfer) as String
            tvToBill.text = resources.getText(R.string.string_empty)
        } else {
            imgArrowTransfer.visibility = View.INVISIBLE
            tvSignEditBill.visibility = View.VISIBLE
            tvFirstParam.text = resources.getText(R.string.param_one_from_bill_v2) // "Счет"
            tvSecondParam.text = resources.getText(R.string.param_two_from_bill_v2) // "Категория"

            tvToBill.text = resources.getText(R.string.string_empty)

            if (listRecords[position] == resources.getText(R.string.button_add_record_income)){  // position == "Доход"
                tvSignEditBill.text = "+"
                currentType = resources.getText(R.string.button_add_record_income) as String
            } else {
                tvSignEditBill.text = "-"
                currentType = resources.getText(R.string.button_add_record_consumption) as String
            }
        }

    }

}
