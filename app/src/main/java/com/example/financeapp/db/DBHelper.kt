package com.example.financeapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.financeapp.network.Model

class DBHelper(val context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private lateinit var user: Model.User

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "finance.db"
        const val TABLE_USER = "user"

        //    Table user
        const val KEY_ID = "_id"

        const val KEY_NAME = "name"
        const val KEY_SURNAME = "surname"
        const val KEY_EMAIL = "email"
        const val KEY_PHOTO_URL = "photo_url"

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
                + KEY_NAME + " text not null, "
                + KEY_SURNAME + " text not null, "
                + KEY_EMAIL + " text not null unique, "
                + KEY_PHOTO_URL + " text, "
                + KEY_PASSWORD + " text, "
                + KEY_GENDER + " text, "
                + KEY_DATE_OF_BIRTH + " text, "
//                + KEY_MOBILE + " text, "
//                + KEY_SIGN_IN + " integer, "
                + KEY_SYNCHRONISE + " integer" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

//    Вставка данных в таблицу User
    fun insertInfAboutUser(name: String, surname: String, email: String, photoUrl: String, password: String, gender: String,
                           dateOfBirth: String, synchronise: Int){

        val db: SQLiteDatabase = writableDatabase

//    val sql = "insert into $TABLE_USER ($KEY_NAME, $KEY_SURNAME, $KEY_EMAIL, $KEY_PHOTO_URL, $KEY_PASSWORD, " +
//            "$KEY_GENDER, $KEY_DATE_OF_BIRTH, $KEY_SYNCHRONISE) values ('$name', '$surname', '$email', '$photoUrl'," +
//            "'$password', '$gender', '$dateOfBirth', $synchronise);"
//    db.execSQL(sql)

        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_SURNAME, surname)
            put(KEY_EMAIL, email)
            put(KEY_PHOTO_URL, photoUrl)
            put(KEY_PASSWORD, password)
            put(KEY_GENDER, gender)
            put(KEY_DATE_OF_BIRTH, dateOfBirth)
            put(KEY_SYNCHRONISE, synchronise)
        }

        // Insert the new row, returning the primary key value of the new row
        //    val newRowId = db.insert(TABLE_USER, null, values)
//        db.insert(TABLE_USER, null, values)
        db.insertWithOnConflict(TABLE_USER,null, values, SQLiteDatabase.CONFLICT_REPLACE);

//    val cursor = db.rawQuery("select * from $TABLE_USER", null)
//    Log.d("myLogs", "Insert cursor.count = ${cursor.count}")
//
//    if (cursor.count > 0) {
//        cursor.moveToFirst()
//
//        dname = cursor.getString(cursor.getColumnIndex(KEY_NAME))
//        dsurname = cursor.getString(cursor.getColumnIndex(KEY_SURNAME))
//
//    }
//
//
////    Log.d("myLogs", "select * from $TABLE_USER")
////    Log.d("myLogs", "newRowId = $newRowId")
//    cursor.close()


//
////        Log.d("myLogs", "cursor.count = ${cursor.count}")
//
//    try {
//        if (cursor.count > 0){
//            cursor.moveToFirst()
//
//            val user = User(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
//                    cursor.getString(cursor.getColumnIndex(KEY_SURNAME)),
//                    cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
//                    cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL)),
//                    cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
//                    cursor.getString(cursor.getColumnIndex(KEY_GENDER)),
//                    cursor.getString(cursor.getColumnIndex(KEY_DATE_OF_BIRTH)),
//                    cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE)))
//
////            if (user != null) {
////                user.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
////                user.surname = cursor.getString(cursor.getColumnIndex(KEY_SURNAME))
////                user.email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
////                user.photoUrl = cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL))
////                user.password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
////                user.gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER))
////                user.dateOfBirth = cursor.getString(cursor.getColumnIndex(KEY_DATE_OF_BIRTH))
////                user.synchronise = cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE))
////            }
//
//            Log.d("myLogs", "user.name = ${user.name}, user.surname = ${user.surname}")
//        }
//    } finally {
//        cursor.close()
//    }


    }

//    Извлечение данных из таблицы User
    fun getInfAboutUser() : Model.User {

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_USER", null)

        Log.d("myLogs", "getInfAboutUser cursor.count = ${cursor.count}")

//    cursor.use { _ ->
//        if (cursor.count > 0){
//            cursor.moveToFirst()
//
//            user = User(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
//                    cursor.getString(cursor.getColumnIndex(KEY_SURNAME)),
//                    cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
//                    cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL)),
//                    cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
//                    cursor.getString(cursor.getColumnIndex(KEY_GENDER)),
//                    cursor.getString(cursor.getColumnIndex(KEY_DATE_OF_BIRTH)),
//                    cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE)))
//        }
//    }

//        user = User("", "", "", "", "", "", "", 0)

//    try {
//
//    } catch (e: kotlin.UninitializedPropertyAccessException){
//
//    }

        if (cursor.count > 0){
            cursor.moveToFirst()

            user = Model.User(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_SURNAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL)),
                    cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_GENDER)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE_OF_BIRTH)),
                    cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE)))
        }

        cursor.close()

        return user

//        if (cursor.moveToFirst()){
//            do {
//                user!!.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
//                user.surname = cursor.getString(cursor.getColumnIndex(KEY_SURNAME))
//                user.email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
//                user.photoUrl = cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL))
//                user.password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
//                user.gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER))
//                user.dateOfBirth = cursor.getString(cursor.getColumnIndex(KEY_DATE_OF_BIRTH))
//                user.synchronise = cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE))
//            } while (cursor.moveToNext())
//
//        }
    }

    fun deleteInfAbutUser(){
        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_USER, null, null);
//        db.execSQL("delete from ${TABLE_USER}")
    }

    fun getUserCount(): Int{
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_USER", null).count
    }
}