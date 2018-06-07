package com.example.financeapp.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.SpinnerCountBillAdapter
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.network.Model

import kotlinx.android.synthetic.main.activity_edit_bill.*
import kotlinx.android.synthetic.main.content_edit_bill.*

class EditBillActivity : BaseActivity() {

    private lateinit var nameBill: String
    var color: Int = 0
    var colorPosition: Int = 0

    private var dialogSave: AlertDialog.Builder? = null
    private var dialogDelete: AlertDialog.Builder? = null

    private lateinit var listBills: ArrayList<Model.Bill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bill)
        setSupportActionBar(toolbarEditBill)

        listBills = dbHelper.getBillsFromDB()

        edNewNameBill.setText(intent.getStringExtra("nameBill"))

        dialogSave = AlertDialog.Builder(this)
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


        dialogDelete = AlertDialog.Builder(this)
                .setMessage(resources.getText(R.string.message_delete_bill))
                .setPositiveButton(resources.getText(R.string.btn_yes), DialogInterface.OnClickListener { dialog, which ->
                    dbHelper.deleteBill(edNewNameBill.text.toString())
                    finish()
                })
                .setNegativeButton(resources.getText(R.string.btn_no), DialogInterface.OnClickListener { dialog, which ->

                })
                .setCancelable(true)
                .setOnCancelListener(DialogInterface.OnCancelListener {

                })

        val listColors = resources.getIntArray(R.array.colorSpinnerBill)

        spinnerEditColorBill.adapter = SpinnerCountBillAdapter(this, listColors)

        spinnerEditColorBill.setSelection(intent.getIntExtra("colorPosition", 0))

//        Выбор цвета
        spinnerEditColorBill.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                colorPosition = position
                color = listColors[position]
            }

        }


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarEditBill.setNavigationOnClickListener {
            if (edNewNameBill.text.toString() != ""){
                if(edNewNameBill.text.toString() != intent.getStringExtra("nameBill")
                        || spinnerEditColorBill.selectedItemPosition != intent.getIntExtra("colorPosition", 0)) {
                    dialogSave!!.show()
                } else {
                    finish()
                }
            } else {
                finish()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_bill, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.btnEditBill -> {
//                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)

                if (edNewNameBill.text.toString() == ""){
                    Snackbar.make(window.decorView, resources.getText(R.string.input_please_name_bill), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                } else {
                    nameBill = edNewNameBill.text.toString()

                    dbHelper.editBill(nameBill, color, colorPosition, 0)

                    finish()
                }
                return true
            }

            R.id.btnDeleteBill -> {
                dialogDelete!!.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
