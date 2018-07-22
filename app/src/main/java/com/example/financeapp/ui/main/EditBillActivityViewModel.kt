package com.example.financeapp.ui.main

import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Model
import com.example.financeapp.network.NetworkHelper

class EditBillActivityViewModel(private val dbHelper: DBHelper, private val networkHelper: NetworkHelper) {

    fun getBillsFromDB(): ArrayList<Model.Bill>{
        return dbHelper.getBillsFromDB()
    }

    fun getBillId(billName: String): Long{
        return dbHelper.getBillId(billName)
    }

    fun updateRecordsBill(oldBillName: String, newBillName: String){
        networkHelper.updateRecordsBill(oldBillName, newBillName)
    }

    fun editBill(oldBillName: String, newBillName: String, color: Int, colorPosition: Int){
        networkHelper.updateBill(oldBillName, newBillName, color, colorPosition)
    }

    fun deleteBill(billName: String){
        networkHelper.deleteBill(billName)
    }

}