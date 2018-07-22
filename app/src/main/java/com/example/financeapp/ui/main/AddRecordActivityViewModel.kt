package com.example.financeapp.ui.main

import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Model
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper

class AddRecordActivityViewModel(private val dbHelper: DBHelper, private val networkHelper: NetworkHelper) {

    fun updateAmountBill(newAmountSum: String, nameBillFrom: String){
        networkHelper.updateAmountBill(newAmountSum, nameBillFrom)
    }

    fun getBillSum(nameBill: String): Double{
        return dbHelper.getBillSum(nameBill)
    }

    fun getRecordById(recordId: Int): Model.Record{
        return dbHelper.getRecordById(recordId)
    }

    fun addNewRecord(name: String, sum: String, bill: String, type: String, color: Int, icon: Int,
                     date: String, deleted: Int, isSynchronize: Int){

        networkHelper.addNewRecord(name, sum, bill, type, color, icon, date, isSynchronize)
    }

    fun deleteRecord(idRecord: Long){
        networkHelper.deleteRecord(idRecord)
    }

    fun updateRecord(idRecord: Long, name: String, sum: String, bill: String, type: String, color: Int, icon: Int, date: String){
        networkHelper.updateRecord(idRecord, name, sum, bill, type, color, icon, date)
    }
}