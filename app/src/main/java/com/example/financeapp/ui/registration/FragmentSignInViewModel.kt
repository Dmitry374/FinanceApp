package com.example.financeapp.ui.registration

import com.example.financeapp.db.DBHelper

class FragmentSignInViewModel(private val dbHelper: DBHelper) {

    fun insertDataInLocalDB(name: String, surname: String, email: String, photourl: String,
                            password: String, gender: String, datebirth: String, synchronise: Int){
        dbHelper.insertInfAboutUser(name, surname, email, photourl, password, gender, datebirth, synchronise)
    }

    fun getUserCount(): Int{
        return dbHelper.getUserCount()
    }
}