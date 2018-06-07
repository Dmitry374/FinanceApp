package com.example.financeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.financeapp.R

import kotlinx.android.synthetic.main.activity_list_bill.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.financeapp.adapter.ListBillsAdapter
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.network.Model
import kotlinx.android.synthetic.main.activity_add_new_bill.*
import kotlinx.android.synthetic.main.content_list_bill.*
import kotlinx.android.synthetic.main.footer_list_bills.*


class ListBillActivity : BaseActivity() {

    private lateinit var listBills: ArrayList<Model.Bill>

    private val REQUEST_CODE_NEW_BILL_ITEM = 222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bill)
        setSupportActionBar(toolbarListBill)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbarListBill.setNavigationOnClickListener {
            finish()
        }

        updateListVew()

        val footerList = View.inflate(this, R.layout.footer_list_bills, null)
        lvListBills.addFooterView(footerList)

        lvListBills.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent()
            intent.putExtra("nameSelectedBill", listBills[position].name)
            intent.putExtra("nameSelectedSecondBill", listBills[position].name)
            setResult(RESULT_OK, intent)
            finish()
        }


//        Добавить новый счет
        btnAddNewRecordListBill.setOnClickListener {
            val intent = Intent(this, AddNewBillActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_BILL_ITEM)
        }

//        Настройки счетов
        btnSettingsNewRecordListBill.setOnClickListener {
            val intent = Intent(this, SettingsBillActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_NEW_BILL_ITEM) {
            updateListVew()
        }
    }

//    Обновление списка
    private fun updateListVew(){
        listBills = if (dbHelper.getBillCount() == 0){
            ArrayList<Model.Bill>()
        } else {
            dbHelper.getBillsFromDB()
        }

        val listAdapter = ListBillsAdapter(this, listBills)

        lvListBills.adapter = listAdapter
    }

}
