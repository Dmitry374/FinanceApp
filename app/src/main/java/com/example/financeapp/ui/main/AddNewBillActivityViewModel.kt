package com.example.financeapp.ui.main

import com.example.financeapp.common.Constants.Companion.INSERT_ROW
import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.NetworkHelper

class AddNewBillActivityViewModel(private val dbHelper: DBHelper, private val networkHelper: NetworkHelper) {

    fun isBillExist(nameBill: String): Boolean {
        return dbHelper.isBillExist(nameBill)
    }

    fun addNewBill(name: String, amount: String, currency: String, color: Int, colorPosition: Int, isSynchronize: Int) {
        networkHelper.addNewBill(name, amount, currency, color, colorPosition, isSynchronize)
    }
}