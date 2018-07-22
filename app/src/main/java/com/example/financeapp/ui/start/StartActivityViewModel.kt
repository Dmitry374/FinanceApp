package com.example.financeapp.ui.start

import com.example.financeapp.db.DBHelper

class StartActivityViewModel(private val dbHelper: DBHelper) {

    fun getUserCountFromDB() = dbHelper.getUserCount()

}