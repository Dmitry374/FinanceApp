package com.example.financeapp.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.SpinnerTypeRecord
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.common.Constants.Companion.UPDATE_ROW
import com.example.financeapp.network.Model

import kotlinx.android.synthetic.main.activity_add_record.*
import kotlinx.android.synthetic.main.content_add_record.*
import kotlinx.android.synthetic.main.content_edit_bill.*
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : GoogleApiClientBaseActivity() {

    private val TAG = AddRecordActivity::class.java.simpleName

    private val REQUEST_CODE_LIST_BILL = 111
    private val REQUEST_CODE_LIST_CATEGORY = 333
    private val REQUEST_CODE_LIST_BILL_SECOND = 555

    lateinit var currentType: String

    lateinit var newAmountBill: String

    var categoryIcon: Int = 0

    lateinit var record: Model.Record

//    var previousSumBill = 0.0
    lateinit var previousNameBill: String

    private var dialogDelete: AlertDialog.Builder? = null

    private var amountBill: Double = 0.0

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


        Log.d("myLogs", "$TAG currentType = $currentType")

        spinnerTypeRecord.adapter = SpinnerTypeRecord(this, listRecords)

        spinnerTypeRecord.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                setSigns(listRecords, position)
            }

        }

//        ----------------------------------------------------------
//        UPDATE RECORD
        if (intent.getStringExtra("type") == UPDATE_ROW){

//            Имя Toolbar при редактировании записи
            supportActionBar!!.title = resources.getText(R.string.title_edit_record)

            spinnerTypeRecord.isEnabled = false

            record = addRecordActivityViewModel.getRecordById(intent.getIntExtra("id", 0))

//            previousSumBill = addRecordActivityViewModel.getBillSum(record.name)
            previousNameBill = record.name

            currentType = record.type

            Log.d("myLogs", "$TAG UPDATE currentType = $currentType")

            edSumNewRecord.setText(record.sum)

            edSumNewRecord.setSelection(edSumNewRecord.text.length)  // Курсор в конец текста

//            when (currentType){
//                resources.getText(R.string.button_add_record_income) as String -> {
//                    setSigns(listRecords, 0)
//                    spinnerTypeRecord.setSelection(0)
//                }
//                resources.getText(R.string.button_add_record_consumption) as String -> {
//                    setSigns(listRecords, 1)
//                    spinnerTypeRecord.setSelection(1)
//                }
//                resources.getText(R.string.button_add_record_transfer) as String -> {
//                    setSigns(listRecords, 2)
//                    spinnerTypeRecord.setSelection(2)
//                }
//            }

            if (currentType == resources.getText(R.string.button_add_record_income) as String){
                setSigns(listRecords, 0)
                spinnerTypeRecord.setSelection(0)
            }

            if (currentType == resources.getText(R.string.button_add_record_consumption) as String){
                setSigns(listRecords, 1)
                spinnerTypeRecord.setSelection(1)
            }

            if (currentType == resources.getText(R.string.button_add_record_transfer) as String){
                setSigns(listRecords, 2)
                spinnerTypeRecord.setSelection(2)
            }

            tvFromBill.text = record.bill

        } else {
            Log.d("myLogs", "$TAG NOT UPDATE currentType = $currentType")
//        Выствить знаки
            setSigns(listRecords, 0)
        }
//        ----------------------------------------------------------



//        --------------- Диалог на удаление -------------------------
        dialogDelete = AlertDialog.Builder(this)
                .setMessage(resources.getText(R.string.message_delete_bill))
                .setPositiveButton(resources.getText(R.string.btn_yes), DialogInterface.OnClickListener { dialog, which ->

                    //                    При удалении возвращаем все в исходную позицию

//                    ------------------- Доход ------------------
                    if (currentType == resources.getText(R.string.button_add_record_income) as String){
                        Toast.makeText(this, "record.bill = ${record.bill}", Toast.LENGTH_SHORT).show()
                        amountBill = addRecordActivityViewModel.getBillSum(record.bill) - record.sum.toDouble()
                        newAmountBill = amountBill.toString()

                        addRecordActivityViewModel.updateAmountBill(newAmountBill, record.bill)
                        addRecordActivityViewModel.deleteRecord(record.id.toLong())
                    }

//                    ------------------- Расход ------------------
                    if (currentType == resources.getText(R.string.button_add_record_consumption) as String){
                        amountBill = addRecordActivityViewModel.getBillSum(record.bill) + record.sum.toDouble()
                        newAmountBill = amountBill.toString()

                        Log.d("myLog", "dialogDelete newAmountBill = $newAmountBill  tvFromBill.text = ${record.bill}")

                        addRecordActivityViewModel.updateAmountBill(newAmountBill, record.bill)
                        addRecordActivityViewModel.deleteRecord(record.id.toLong())
                    }

//                    ------------------- Перевод ------------------
                    if (currentType == resources.getText(R.string.button_add_record_transfer) as String){
                        amountBill = addRecordActivityViewModel.getBillSum(record.bill) + record.sum.toDouble()
                        newAmountBill = amountBill.toString()
                        Log.d("myLogs", "$TAG 1.   ${addRecordActivityViewModel.getBillSum(record.bill)} + ${record.sum.toDouble()} - ${edSumNewRecord.text.toString().toDouble()} = ${addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + record.sum.toDouble() - edSumNewRecord.text.toString().toDouble()}")

                        addRecordActivityViewModel.updateAmountBill(newAmountBill, record.bill)

//                                -----------------------
                        amountBill = addRecordActivityViewModel.getBillSum(record.name) - record.sum.toDouble()
                        newAmountBill = amountBill.toString()
                        Log.d("myLogs", "$TAG 2.    ${addRecordActivityViewModel.getBillSum(record.name)} - ${record.sum.toDouble()} = ${addRecordActivityViewModel.getBillSum(record.name) - record.sum.toDouble()}")

                        addRecordActivityViewModel.updateAmountBill(newAmountBill, previousNameBill)
//                                -----------------------

                        addRecordActivityViewModel.deleteRecord(record.id.toLong())
                    }

                    finish()
                })
                .setNegativeButton(resources.getText(R.string.btn_no), DialogInterface.OnClickListener { dialog, which ->

                })
                .setCancelable(true)
                .setOnCancelListener(DialogInterface.OnCancelListener {

                })
//        --------------------------------------------------------------




        linBtnFirstParam.setOnClickListener {
//            Toast.makeText(this, "record.bill = ${record.bill}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListBillActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LIST_BILL)
        }

        linBtnSecondParam.setOnClickListener {
            secondParameterClick()
        }

//        Добавление новой записи
//        btnAddNewRecord.setOnClickListener {
//            Log.d("myLogs", "$TAG currentType = $currentType")
//
//            if (edSumNewRecord.text.toString().trim() == ""){
//                Toast.makeText(this, resources.getText(R.string.enter_sum), Toast.LENGTH_SHORT).show()
//            } else {
//
////                ---------------------- Доход -------------------------
//
//                if (currentType == resources.getText(R.string.button_add_record_income) as String){
//
////                    Add Record
//                    if ((tvToBill.text as String).toLowerCase()
//                            == (resources.getText(R.string.string_empty) as String).toLowerCase()){
//                        secondParameterClick()
//                        Toast.makeText(this, resources.getText(R.string.text_select_category), Toast.LENGTH_SHORT).show()
//                    } else {
//
////                        Add Bill
////                        val amountBill = dbHelper.getBillSum(tvFromBill.text as String) + edSumNewRecord.text.toString().toDouble()
//                        val amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + edSumNewRecord.text.toString().toDouble()
//                        newAmountBill = amountBill.toString()
//
////                        dbHelper.updateAmountBill(newAmountBill, tvFromBill.text as String)
//                        addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)
//
////                        Add record
//                        val currentDate = sdf.format(Date())
//
////                        dbHelper.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
////                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
////                                ContextCompat.getColor(this, R.color.color_income),
////                                categoryIcon, currentDate,1, 0)
//
//                        addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
//                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
//                                ContextCompat.getColor(this, R.color.color_income),
//                                categoryIcon, currentDate,1, 0)
//
//                        finish()
//                    }
//
//
//                }
//
////                ----------------------- Рассход ----------------------
//                if (currentType == resources.getText(R.string.button_add_record_consumption) as String){
//
////                    Add Record
//                    if ((tvToBill.text as String).toLowerCase()
//                            == (resources.getText(R.string.string_empty) as String).toLowerCase()){
//                        secondParameterClick()
//                        Toast.makeText(this, resources.getText(R.string.text_select_category), Toast.LENGTH_SHORT).show()
//                    } else {
//
////                        Add Bill
//                        val amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
//                        newAmountBill = amountBill.toString()
//
//                        addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)
//
////                        Add record
//                        val currentDate = sdf.format(Date())
//
//                        addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), "${edSumNewRecord.text}",
//                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_consumption).toString(),
//                                ContextCompat.getColor(this, R.color.color_consumption),
//                                categoryIcon, currentDate, 1, 0)
//
//                        finish()
//                    }
//
//                }
//
////                ------------------------ Перевод ----------------------
//                if (currentType == resources.getText(R.string.button_add_record_transfer) as String){
//
//                    if (tvToBill.text.toString().toLowerCase() == (resources.getText(R.string.string_empty) as String).toLowerCase()){
//                        Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
//                    } else if (tvFromBill.text.toString() == tvToBill.text.toString()){
//                        Toast.makeText(this, resources.getText(R.string.text_select_another_bill), Toast.LENGTH_SHORT).show()
//                    } else {
////                        Add Bill
//                        var amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
//                        newAmountBill = amountBill.toString()
//
//                        addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)
//
//                        amountBill = addRecordActivityViewModel.getBillSum(tvToBill.text as String) + edSumNewRecord.text.toString().toDouble()
//                        newAmountBill = amountBill.toString()
//
//                        addRecordActivityViewModel.updateAmountBill(newAmountBill, tvToBill.text as String)
//
//
//                        if ((tvToBill.text as String).toLowerCase()
//                                == (resources.getText(R.string.string_empty) as String).toLowerCase()){
//                            secondParameterClick()
//                            Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
//                        } else {
//
//                            val currentDate = sdf.format(Date())
//
//                            addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
//                                    tvFromBill.text.toString(), resources.getText(R.string.button_add_record_transfer).toString(),
//                                    ContextCompat.getColor(this, R.color.color_transfer),
//                                    R.drawable.transfer, currentDate, 1, 0)
//
//                            finish()
//                        }
//                    }
//
//                }
//
//
//
//
//
//            }
//        }

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

        Log.d("myLogs", "$TAG listRecords[position] = ${listRecords[position]}")

        if (listRecords[position] == resources.getText(R.string.button_add_record_transfer)){  // position == "Перевод"
            imgArrowTransfer.visibility = View.VISIBLE
            tvSignEditBill.visibility = View.INVISIBLE
            tvFirstParam.text = resources.getText(R.string.param_one_from_bill_v1) // "Со счета"
            tvSecondParam.text = resources.getText(R.string.param_two_from_bill_v1) // "На счет"

            currentType = resources.getText(R.string.button_add_record_transfer) as String

            if (intent.getStringExtra("type") == UPDATE_ROW){
                tvToBill.text = record.name
            } else {
                tvToBill.text = resources.getText(R.string.string_empty)
            }
        } else {
            imgArrowTransfer.visibility = View.INVISIBLE
            tvSignEditBill.visibility = View.VISIBLE
            tvFirstParam.text = resources.getText(R.string.param_one_from_bill_v2) // "Счет"
            tvSecondParam.text = resources.getText(R.string.param_two_from_bill_v2) // "Категория"

            if (intent.getStringExtra("type") == UPDATE_ROW){
                tvToBill.text = record.name
            } else {
                tvToBill.text = resources.getText(R.string.string_empty)
            }

            if (listRecords[position] == resources.getText(R.string.button_add_record_income)){  // position == "Доход"
                tvSignEditBill.text = "+"
                currentType = resources.getText(R.string.button_add_record_income) as String
            } else {
                tvSignEditBill.text = "-"
                currentType = resources.getText(R.string.button_add_record_consumption) as String
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.add_record, menu)
        return true
    }

    @SuppressLint("SimpleDateFormat")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val sdf = SimpleDateFormat("dd.MM.yyyy")

        when (item.itemId) {
            R.id.btnDeleteRecord -> {
                if (intent.getStringExtra("type") != UPDATE_ROW){
                    onBackPressed()
                } else {
                    dialogDelete!!.show()
                }
                return true
            }
            R.id.btnCreateNewRecord -> {

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

//                            IF UPDATE RECORD
                            if (intent.getStringExtra("type") == UPDATE_ROW){

                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) - record.sum.toDouble() + edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)




                                if (categoryIcon == 0){
                                    categoryIcon = record.icon
                                }

                                addRecordActivityViewModel.updateRecord(record.id.toLong(), tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                        tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
                                        ContextCompat.getColor(this, R.color.color_income),
                                        categoryIcon, record.date)
                            } else {

//                                Add Bill
//                        val amountBill = dbHelper.getBillSum(tvFromBill.text as String) + edSumNewRecord.text.toString().toDouble()
                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

//                        dbHelper.updateAmountBill(newAmountBill, tvFromBill.text as String)
                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)

//                        Add record
                                val currentDate = sdf.format(Date())

//                        dbHelper.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
//                                tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
//                                ContextCompat.getColor(this, R.color.color_income),
//                                categoryIcon, currentDate,1, 0)

                                addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                        tvFromBill.text.toString(), resources.getText(R.string.button_add_record_income).toString(),
                                        ContextCompat.getColor(this, R.color.color_income),
                                        categoryIcon, currentDate,1, 0)

                            }

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

//                            IF UPDATE RECORD
                            if (intent.getStringExtra("type") == UPDATE_ROW){

                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + record.sum.toDouble() - edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)




                                if (categoryIcon == 0){
                                    categoryIcon = record.icon
                                }

                                addRecordActivityViewModel.updateRecord(record.id.toLong(), tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                        tvFromBill.text.toString(), resources.getText(R.string.button_add_record_consumption).toString(),
                                        ContextCompat.getColor(this, R.color.color_consumption),
                                        categoryIcon, record.date)

                            } else {

//                                Add Bill
                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)

//                                Add record
                                val currentDate = sdf.format(Date())

                                addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), "${edSumNewRecord.text}",
                                        tvFromBill.text.toString(), resources.getText(R.string.button_add_record_consumption).toString(),
                                        ContextCompat.getColor(this, R.color.color_consumption),
                                        categoryIcon, currentDate, 1, 0)

                            }


                            finish()
                        }

                    }

//                ------------------------ Перевод ----------------------
                    if (currentType == resources.getText(R.string.button_add_record_transfer) as String){

                        var amountBill: Double

                        if (tvToBill.text.toString().toLowerCase() == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                            Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
                        } else if (tvFromBill.text.toString() == tvToBill.text.toString()){
                            Toast.makeText(this, resources.getText(R.string.text_select_another_bill), Toast.LENGTH_SHORT).show()
                        } else {

//                            IF UPDATE RECORD
                            if (intent.getStringExtra("type") == UPDATE_ROW){

                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + record.sum.toDouble() - edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()
                                Log.d("myLogs", "$TAG 1.   ${addRecordActivityViewModel.getBillSum(tvFromBill.text as String)} + ${record.sum.toDouble()} - ${edSumNewRecord.text.toString().toDouble()} = ${addRecordActivityViewModel.getBillSum(tvFromBill.text as String) + record.sum.toDouble() - edSumNewRecord.text.toString().toDouble()}")

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)




//                                -----------------------
//                                amountBill = previousSumBill - record.sum.toDouble()
                                amountBill = addRecordActivityViewModel.getBillSum(record.name) - record.sum.toDouble()
                                newAmountBill = amountBill.toString()
                                Log.d("myLogs", "$TAG 2.    ${addRecordActivityViewModel.getBillSum(record.name)} - ${record.sum.toDouble()} = ${addRecordActivityViewModel.getBillSum(record.name) - record.sum.toDouble()}")

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, previousNameBill)
//                                -----------------------



                                amountBill = addRecordActivityViewModel.getBillSum(tvToBill.text as String) + edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()
                                Log.d("myLogs", "$TAG 3.     ${addRecordActivityViewModel.getBillSum(tvToBill.text as String)} + ${edSumNewRecord.text.toString().toDouble()} = $amountBill")

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvToBill.text as String)


                                if ((tvToBill.text as String).toLowerCase()
                                        == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                                    secondParameterClick()
                                    Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
                                } else {

                                    val currentDate = sdf.format(Date())

                                    addRecordActivityViewModel.updateRecord(record.id.toLong(), tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                            tvFromBill.text.toString(), resources.getText(R.string.button_add_record_transfer).toString(),
                                            ContextCompat.getColor(this, R.color.color_transfer),
                                            R.drawable.transfer, currentDate)

                                }

                            } else {

//                                Add Bill
                                amountBill = addRecordActivityViewModel.getBillSum(tvFromBill.text as String) - edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvFromBill.text as String)

                                amountBill = addRecordActivityViewModel.getBillSum(tvToBill.text as String) + edSumNewRecord.text.toString().toDouble()
                                newAmountBill = amountBill.toString()

                                addRecordActivityViewModel.updateAmountBill(newAmountBill, tvToBill.text as String)

                                if ((tvToBill.text as String).toLowerCase()
                                        == (resources.getText(R.string.string_empty) as String).toLowerCase()){
                                    secondParameterClick()
                                    Toast.makeText(this, resources.getText(R.string.text_select_bill_to_transfer), Toast.LENGTH_SHORT).show()
                                } else {

                                    val currentDate = sdf.format(Date())

                                    addRecordActivityViewModel.addNewRecord(tvToBill.text.toString(), edSumNewRecord.text.toString(),
                                            tvFromBill.text.toString(), resources.getText(R.string.button_add_record_transfer).toString(),
                                            ContextCompat.getColor(this, R.color.color_transfer),
                                            R.drawable.transfer, currentDate, 1, 0)

                                }

                            }


                            finish()
                        }

                    }



                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
