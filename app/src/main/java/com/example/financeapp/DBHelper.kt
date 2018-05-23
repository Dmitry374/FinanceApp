package com.example.financeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context, private val contentValues: ContentValues)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "finance.db"
        const val TABLE_USER = "user"

        //    Table user
        const val KEY_ID = "_id"

        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_EMAIL = "email"

        const val KEY_PASSWORD = "password"
        const val KEY_GENDER = "gender"
        const val KEY_DATE_OF_BIRTH = "date_of_birth"
        const val KEY_SYNCHRONISE = "key_synchronise"

//        const val KEY_MOBILE = "mobile_phone"

//        const val KEY_SIGN_IN = "key_sign"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table " + TABLE_USER + "("
                + KEY_ID + " integer primary key autoincrement,"
                + KEY_EMAIL + " text not null unique, "
                + KEY_NAME + " text not null, "
                + KEY_SURNAME + "text, "
                + KEY_GENDER + "text, "
                + KEY_DATE_OF_BIRTH + " text, "
//                + KEY_MOBILE + " text, "
                + KEY_PASSWORD + " text, "
//                + KEY_SIGN_IN + " integer, "
                + KEY_SYNCHRONISE + " integer" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertInfAboutUser(name: String, surname: String, email: String, password: String, gender: String,
                           dateOfBirth: String, synchronise: Int){

        val db: SQLiteDatabase = writableDatabase

        contentValues.put(DBHelper.KEY_NAME, name)
        contentValues.put(DBHelper.KEY_SURNAME, surname)
        contentValues.put(DBHelper.KEY_EMAIL, email)
        contentValues.put(DBHelper.KEY_PASSWORD, password)
        contentValues.put(DBHelper.KEY_GENDER, gender)
        contentValues.put(DBHelper.KEY_DATE_OF_BIRTH, dateOfBirth)
        contentValues.put(DBHelper.KEY_SYNCHRONISE, synchronise)

        db.insert(DBHelper.TABLE_USER, null, contentValues)
        contentValues.clear()
    }
}