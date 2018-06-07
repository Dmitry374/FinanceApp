package com.example.financeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.RecyclerSettingsBillAdapter
import com.example.financeapp.base.BaseActivity
import com.example.financeapp.network.Model

import kotlinx.android.synthetic.main.activity_settings_bill.*
import kotlinx.android.synthetic.main.content_settings_bill.*

class SettingsBillActivity : BaseActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var listBills: ArrayList<Model.Bill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_bill)
        setSupportActionBar(toolbarBillsSettings)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerBillsSettings.layoutManager = linearLayoutManager

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbarBillsSettings.setNavigationOnClickListener { finish() }

        updateRecycler()

    }

    private fun updateRecycler(){

        listBills = dbHelper.getBillsFromDB()

        recyclerBillsSettings.adapter = RecyclerSettingsBillAdapter(this, listBills, object : RecyclerSettingsBillAdapter.OnItemClickListener {
            override fun onItemClick(item: Model.Bill, position: Int) {
                val intent = Intent(this@SettingsBillActivity, EditBillActivity::class.java)
//                Отправить имя счета и цвет
                intent.putExtra("nameBill", listBills[position].name)
                intent.putExtra("colorPosition", listBills[position].colorPosition)
                startActivity(intent)
            }

        })

    }

    override fun onResume() {
        super.onResume()

        updateRecycler()
    }
}
