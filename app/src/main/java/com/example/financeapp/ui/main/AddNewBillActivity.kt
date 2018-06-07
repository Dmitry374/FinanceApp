package com.example.financeapp.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.example.financeapp.R
import com.example.financeapp.adapter.SpinnerCountBillAdapter
import com.example.financeapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_bill.*
import java.text.DecimalFormat
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


class AddNewBillActivity : BaseActivity() {

    private lateinit var nameBill: String
    private lateinit var amountBill: String
    var color: Int = 0
    var colorPosition: Int = 0

    var dialog: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_bill)
        setSupportActionBar(toolbar)

//        edInitialValueBill.addTextChangedListener(commonMethod.onTextChangedListener(edInitialValueBill))

//        Знначение по умолчанию
        edInitialValueBill.setText("0.0")

//        При установки фокуса на edInitial строка становиться чистой
        edInitialValueBill.onFocusChangeListener = View.OnFocusChangeListener { view: View, b: Boolean ->
            if (edInitialValueBill.hasFocus()){
                edInitialValueBill.setText("")
            }
        }

        dialog = AlertDialog.Builder(this)
                .setTitle(resources.getText(R.string.title_ad_new_bill))
                .setMessage(resources.getText(R.string.message_ad_new_bill))
                .setPositiveButton(resources.getText(R.string.btn_yes), DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
                .setNegativeButton(resources.getText(R.string.btn_no), DialogInterface.OnClickListener { dialog, which ->

                })
                .setCancelable(true)
                .setOnCancelListener(DialogInterface.OnCancelListener {

                })

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            if (edNameBill.text.toString() != ""){
                dialog!!.show()
            } else {
                finish()
            }
        }

        val listColors = resources.getIntArray(R.array.colorSpinnerBill)

        spinnerAddCountBill.adapter = SpinnerCountBillAdapter(this, listColors)

//        Выбор цвета
        spinnerAddCountBill.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                colorPosition = position
                color = listColors[position]
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_bill, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.btnCreateNewBill -> {
//                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)

                if (edNameBill.text.toString() == ""){
                    Snackbar.make(window.decorView, resources.getText(R.string.input_please_name_bill), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                } else {
                    nameBill = edNameBill.text.toString()
                    amountBill = edInitialValueBill.text.toString()

                    if(dbHelper.isBillExist(nameBill)){
                        Toast.makeText(this, resources.getText(R.string.this_bill_exist), Toast.LENGTH_SHORT).show()
                    } else {
                        dbHelper.addNewBill(nameBill, amountBill, "RUB", color, colorPosition, 0)
                        finish()
                    }
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
