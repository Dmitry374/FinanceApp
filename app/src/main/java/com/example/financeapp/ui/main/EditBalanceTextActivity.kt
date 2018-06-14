package com.example.financeapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_balance_text.*
import kotlinx.android.synthetic.main.content_edit_balance_text.*

class EditBalanceTextActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_balance_text)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)    //   Стрелка
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        edBalanceText.setText(intent.getStringExtra("titleBalance"))

        edBalanceText.setSelection(edBalanceText.text.length)  // Курсор в конец текста
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

                if (edBalanceText.text.toString() == ""){
                    Toast.makeText(this, getText(R.string.text_fill_all_the_fields), Toast.LENGTH_SHORT).show()
                } else {
                    sPrefHelper.setTextCardBalance(edBalanceText.text.toString())
                    finish()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
