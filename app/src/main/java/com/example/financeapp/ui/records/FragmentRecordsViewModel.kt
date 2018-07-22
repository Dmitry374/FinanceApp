package com.example.financeapp.ui.records

import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Model
import com.example.financeapp.sharedpreference.SharedPreferenceHelper

class FragmentRecordsViewModel(private val dbHelper: DBHelper, private val sharedPreferenceHelper: SharedPreferenceHelper) {

    fun getListBillsName(): ArrayList<String>{
        return dbHelper.getListBillsName()
    }

    fun getAllRecordsInReverseOrder(): ArrayList<Model.Record>{
        return dbHelper.getAllRecordsInReverseOrder()
    }

    fun getAllRecordsByBillReverseOrder(nameBill: String): ArrayList<Model.Record>{
        return dbHelper.getAllRecordsByBillReverseOrder(nameBill)
    }

    fun setRemindSelectedBill(posBill: Int){
        sharedPreferenceHelper.setRemindSelectedBill(posBill)
    }

    fun getRemindSelectedBill(): Int {
        return sharedPreferenceHelper.getRemindSelectedBill()
    }

    fun getBillCount(): Int{
        return dbHelper.getBillCount()
    }

    fun getRecordsCount(): Int{
        return dbHelper.getRecordsCount()
    }

    fun getRecordsCountByBill(nameBill: String): Int{
        return dbHelper.getRecordsCountByBill(nameBill)
    }
}