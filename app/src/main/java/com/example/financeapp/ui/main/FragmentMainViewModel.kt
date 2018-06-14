package com.example.financeapp.ui.main

import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Model
import com.example.financeapp.network.NetworkHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper

class FragmentMainViewModel(private val dbHelper: DBHelper, private val sPrefHelper: SharedPreferenceHelper,
                            private val networkHelper: NetworkHelper) {

    fun setRemindSelectedBill(posBill: Int){
        sPrefHelper.setRemindSelectedBill(posBill)
    }

    fun getBillCount(): Int{
        return dbHelper.getBillCount()
    }

    fun getBillsFromDB(): ArrayList<Model.Bill> {
        return dbHelper.getBillsFromDB()
    }

    fun getRemindSelectedBill(): Int {
        return sPrefHelper.getRemindSelectedBill()
    }

    fun getBillBalance(): Double {
        return dbHelper.getBillBalance()
    }

    fun getRecordsCount(): Int{
        return dbHelper.getRecordsCount()
    }

    fun getRecordsCountByBill(nameBill: String): Int{
        return dbHelper.getRecordsCountByBill(nameBill)
    }

    fun getRecordsFromDBByBill(nameBill: String): ArrayList<Model.Record> {
        return dbHelper.getRecordsFromDBByBill(nameBill)
    }

    fun getLastRecordInReverseOrder(nameBill: String, limit: Int): ArrayList<Model.Record>{
        return dbHelper.getLastRecordInReverseOrder(nameBill, limit)
    }


    fun getTitleBalance(): String{
        return sPrefHelper.getTextCardBalance()
    }

    fun getTitleLastRecord(): String{
        return sPrefHelper.getTextCardLastRecords()
    }

    fun getRecordLimit(): Int{
        return sPrefHelper.getLimitCardRecords()
    }

    fun getRecordLimitPosition(): Int{
        return sPrefHelper.getLimitCardRecordsPosition()
    }


}