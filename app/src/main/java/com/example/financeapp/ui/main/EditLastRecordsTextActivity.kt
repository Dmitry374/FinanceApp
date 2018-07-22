package com.example.financeapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.SpinnerKolRecordsAdapter
import com.example.financeapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_last_records_text.*
import kotlinx.android.synthetic.main.content_edit_last_records_text.*

class EditLastRecordsTextActivity : BaseActivity() {

    var selectedLimitRecords = 5
    var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_last_records_text)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

//        Массив количество записей
        val listLimit = resources.getIntArray(R.array.limit_last_records)

        edLastRecordsText.setText(intent.getStringExtra("titleLastRecords"))

        edLastRecordsText.setSelection(edLastRecordsText.text.length)  // Курсор в конец текста

        spinnerLimitLastRecords.adapter = SpinnerKolRecordsAdapter(this, listLimit)

        spinnerLimitLastRecords.setSelection(intent.getIntExtra("positionList", 0))

        spinnerLimitLastRecords.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                selectedLimitRecords = listLimit[position]
                selectedPosition = position
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ok, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when(item!!.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.btnCreateNewBill -> {

                if (edLastRecordsText.text.toString() == ""){
                    Toast.makeText(this, getText(R.string.text_fill_all_the_fields), Toast.LENGTH_SHORT).show()
                } else {
                    sPrefHelper.setTextCardLastRecords(edLastRecordsText.text.toString())
                    sPrefHelper.setLimitCardRecords(selectedLimitRecords)
                    sPrefHelper.setLimitCardRecordsPosition(selectedPosition)
                    finish()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
