package com.example.financeapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.financeapp.network.Model

class DBHelper(val context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private lateinit var user: Model.User
    private lateinit var bill: Model.Bill
    private lateinit var record: Model.Record

    private var id: Long = -1

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

        const val TABLE_BILLS = "bills"

        const val KEY_ID_BILL = "_id"
        const val KEY_NAME_BILL = "name"
        const val KEY_AMOUNT_BILL = "amount"
        const val KEY_CURRENCY_BILL = "currency"
        const val KEY_COLOR_BILL = "color"
        const val KEY_COLOR_BILL_POSITION = "color_position"
        const val KEY_DELETED_BILL = "deleted"
        const val KEY_SYNCHRONISE_BILL = "synchronise"

        const val TABLE_RECORDS = "records"

        const val KEY_ID_RECORD = "_id"
        const val KEY_NAME_RECORD = "name"
        const val KEY_SUM_OP_RECORD = "sum"
        const val KEY_BILL_RECORD = "bill"
        const val KEY_TYPE_RECORD = "type"
        const val KEY_COLOR_RECORD = "color"
        const val KEY_ICON_RECORD = "icon"
        const val KEY_DATE_RECORD = "date"
        const val KEY_DELETED_RECORD = "deleted"
        const val KEY_SYNCHRONISE_RECORD = "synchronise"
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

        db.execSQL("create table " + TABLE_BILLS + "("
                + KEY_ID_BILL + " integer primary key autoincrement,"
                + KEY_NAME_BILL + " text not null unique, "
                + KEY_AMOUNT_BILL + " text, "
                + KEY_CURRENCY_BILL + " text not null, "
                + KEY_COLOR_BILL + " integer, "
                + KEY_COLOR_BILL_POSITION + " integer, "
                + KEY_DELETED_BILL + " integer, "
                + KEY_SYNCHRONISE_BILL + " integer" + ")")


        db.execSQL("create table " + TABLE_RECORDS + "("
                + KEY_ID_RECORD + " integer primary key autoincrement,"
                + KEY_NAME_RECORD + " text not null, "
                + KEY_SUM_OP_RECORD + " text not null, "
                + KEY_BILL_RECORD + " text, "
                + KEY_TYPE_RECORD + " text, "
                + KEY_COLOR_RECORD + " integer, "
                + KEY_ICON_RECORD + " integer, "
                + KEY_DATE_RECORD + " text, "
                + KEY_DELETED_RECORD + " integer, "
                + KEY_SYNCHRONISE_RECORD + " integer" + ")")

        /*
        *
        * const val TABLE_RECORDS = "records"

        const val KEY_ID_RECORD = "_id"
        const val KEY_NAME_RECORD = "name"
        const val KEY_TYPE_RECORD = "type"
        const val KEY_COLOR_RECORD = "color"
        const val KEY_ICON_RECORD = "icon"
        const val KEY_DATE_RECORD = "date"
        const val KEY_SYNCHRONISE_RECORD = "synchronise"
        *
        * */
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
        db.insertWithOnConflict(TABLE_USER,null, values, SQLiteDatabase.CONFLICT_REPLACE)

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

    fun updateInfAboutUser(name: String, surname: String, photoUrl: String,
                           gender: String, dateOfBirth: String, synchronise: Int){

        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_USER set $KEY_NAME = '$name', $KEY_SURNAME = '$surname', " +
                "$KEY_PHOTO_URL = '$photoUrl', $KEY_GENDER = '$gender', " +
                "$KEY_DATE_OF_BIRTH = '$dateOfBirth', $KEY_SYNCHRONISE = '$synchronise';"
        db.execSQL(sql)

    }

    fun updatePassword(password: String, synchronise: Int){

        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_USER set $KEY_PASSWORD = '$password', $KEY_SYNCHRONISE = '$synchronise';"
        db.execSQL(sql)

    }

//    Извлечение данных из таблицы User
    fun getInfAboutUser() : Model.User {

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_USER", null)

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

    fun deleteAllData(){
        val db: SQLiteDatabase = writableDatabase
//        db.delete(TABLE_USER, null, null)
//        db.delete(TABLE_BILLS, null, null)
//        db.delete(TABLE_RECORDS, null, null)

        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BILLS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECORDS")
        onCreate(db)
    }

    fun getUserCount(): Int{
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_USER", null).count
    }

    /*
    *
    *
    * const val KEY_ID_BILL = "_id"
        const val KEY_NAME_BILL = "name"
        const val KEY_AMOUNT_BILL = "amount"
        const val KEY_CURRENCY_BILL = "currency"
        const val KEY_COLOR_BILL = "color"
        const val KEY_SYNCHRONISE_BILL = "synchronise_bill"
    *
    * */

    fun getBillCount(): Int {
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_BILLS where $KEY_DELETED_BILL != '0'", null).count
    }

    fun getBillSum(nameBill: String): Double {
        var sumBill = ""

        val db: SQLiteDatabase = readableDatabase

        val sql = "select $KEY_AMOUNT_BILL from $TABLE_BILLS where $KEY_NAME_BILL = '$nameBill';"

        val cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()){
            do {
                sumBill = cursor.getString(cursor.getColumnIndex(KEY_AMOUNT_BILL))
            } while (cursor.moveToNext())
        }

        cursor.close()

        return sumBill.toDouble()
    }

    fun updateAmountBill(amountBill: String, nameBill: String){

        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_BILLS set $KEY_AMOUNT_BILL = '$amountBill' where $KEY_NAME_BILL = '$nameBill';"
        db.execSQL(sql)

    }

    fun getBillsFromDB(): ArrayList<Model.Bill> {

        val listBills = ArrayList<Model.Bill>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_BILLS where $KEY_DELETED_BILL != '0'", null)

        if (cursor.moveToFirst()){
            do {

                bill = Model.Bill(cursor.getString(cursor.getColumnIndex(KEY_NAME_BILL)),
                        cursor.getString(cursor.getColumnIndex(KEY_AMOUNT_BILL)),
                        cursor.getString(cursor.getColumnIndex(KEY_CURRENCY_BILL)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_BILL)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_BILL_POSITION)),
                        cursor.getInt(cursor.getColumnIndex(KEY_DELETED_BILL)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_BILL)))

                listBills.add(bill)

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listBills
    }


    fun getListBillsName(): ArrayList<String>{

        val listBills = ArrayList<String>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select $KEY_NAME_BILL from $TABLE_BILLS where $KEY_DELETED_BILL != '0'", null)

        if (cursor.moveToFirst()){
            do {

                listBills.add(cursor.getString(cursor.getColumnIndex(KEY_NAME_BILL)))

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listBills

    }

    fun getBillId(name: String): Long{
        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select $KEY_ID_BILL from $TABLE_BILLS WHERE $KEY_NAME_BILL = '$name'", null)

        if (cursor.moveToFirst()){
            id = cursor.getLong(cursor.getColumnIndex(KEY_ID_BILL))
        }

        cursor.close()

        return id
    }

    fun isBillExist(nameBill: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_BILLS where $KEY_NAME_BILL = '$nameBill'", null).count > 0
    }

    fun getBillBalance() : Double{
        var balance = 0.0

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select $KEY_AMOUNT_BILL from $TABLE_BILLS", null)

        if (cursor.moveToFirst()){
            do {

                try{
                    balance += cursor.getString(cursor.getColumnIndex(KEY_AMOUNT_BILL)).toDouble()
                } catch (e: java.lang.NumberFormatException){
                    balance = 0.0
                }

            } while (cursor.moveToNext())

        }

        cursor.close()

        return balance


    }

    fun addNewBill(name: String, amount: String, currency: String, color: Int,
                   colorPosition: Int, deleted: Int, isSynchronize: Int): Long{
        val db: SQLiteDatabase = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_NAME_BILL, name)
        contentValues.put(KEY_AMOUNT_BILL, amount)
        contentValues.put(KEY_CURRENCY_BILL, currency)
        contentValues.put(KEY_COLOR_BILL, color)
        contentValues.put(KEY_COLOR_BILL_POSITION, colorPosition)
        contentValues.put(KEY_DELETED_BILL, deleted)
        contentValues.put(KEY_SYNCHRONISE_BILL, isSynchronize)

        return db.insert(TABLE_BILLS, null, contentValues)

//        val sql = "insert into $TABLE_BILLS ($KEY_NAME_BILL, $KEY_AMOUNT_BILL, $KEY_CURRENCY_BILL, " +
//                "$KEY_COLOR_BILL, $KEY_COLOR_BILL_POSITION, $KEY_SYNCHRONISE_BILL) values ('$name', '$amount', '$currency', $color, " +
//                "$colorPosition, $isSynchronize);"
//        db.execSQL(sql)
    }

    fun manageSynchronisationBill(isSynchronize: Int, billId: Long) {
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_BILLS SET $KEY_SYNCHRONISE_BILL = $isSynchronize WHERE $KEY_ID_BILL = $billId;"
        db.execSQL(sql)
    }



    fun editBill(oldBillName: String, newBillName: String, color: Int, colorPosition: Int, isSynchronize: Int){
        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_BILLS set $KEY_NAME_BILL = '$newBillName', $KEY_COLOR_BILL = '$color', $KEY_COLOR_BILL_POSITION = '$colorPosition', " +
                "$KEY_SYNCHRONISE_BILL = '$isSynchronize' where $KEY_NAME_BILL = '$oldBillName';"
        db.execSQL(sql)
    }

    fun deleteBill(billId: Long){
        val db: SQLiteDatabase = writableDatabase
        val sql = "delete from $TABLE_BILLS where $KEY_ID_BILL = '$billId';"
        db.execSQL(sql)
    }

    fun manageDeletedState(deleted: Int, billId: Long){
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_BILLS SET $KEY_DELETED_BILL = $deleted WHERE $KEY_ID_BILL = $billId;"
        db.execSQL(sql)
    }





    fun addNewRecord(name: String, sum: String, bill: String, type: String, color: Int, icon: Int,
                     date: String, deleted: Int, isSynchronize: Int): Long{

        val db: SQLiteDatabase = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_NAME_RECORD, name)
        contentValues.put(KEY_SUM_OP_RECORD, sum)
        contentValues.put(KEY_BILL_RECORD, bill)
        contentValues.put(KEY_TYPE_RECORD, type)
        contentValues.put(KEY_COLOR_RECORD, color)
        contentValues.put(KEY_ICON_RECORD, icon)
        contentValues.put(KEY_DATE_RECORD, date)
        contentValues.put(KEY_DELETED_RECORD, deleted)
        contentValues.put(KEY_SYNCHRONISE_RECORD, isSynchronize)

        return db.insert(TABLE_RECORDS, null, contentValues)

//        val sql = "insert into $TABLE_RECORDS ($KEY_NAME_RECORD, $KEY_SUM_OP_RECORD, $KEY_BILL_RECORD, $KEY_TYPE_RECORD, " +
//                "$KEY_COLOR_RECORD, $KEY_ICON_RECORD, $KEY_DATE_RECORD, $KEY_SYNCHRONISE_RECORD) values ('$name', " +
//                "'$sum', '$bill', '$type', $color, $icon, '$date', $isSynchronize);"
//        db.execSQL(sql)

    }

    fun getRecordsFromDBByBill(nameBill: String): ArrayList<Model.Record> {

        val listRecords = ArrayList<Model.Record>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_RECORDS where $KEY_BILL_RECORD = '$nameBill' and $KEY_DELETED_RECORD != '0'", null)

        if (cursor.moveToFirst()){
            do {

                record = Model.Record(cursor.getInt(cursor.getColumnIndex(KEY_ID_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_SUM_OP_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_BILL_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_TYPE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ICON_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_DELETED_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_RECORD)))

                listRecords.add(record)

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listRecords

    }

    fun getLastRecordInReverseOrder(nameBill: String, limit: Int): ArrayList<Model.Record>{

        val listRecords = ArrayList<Model.Record>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_RECORDS WHERE $KEY_BILL_RECORD = '$nameBill' AND $KEY_DELETED_RECORD != '0' order by $KEY_ID_RECORD DESC limit $limit;", null)

        if (cursor.moveToFirst()){
            do {

                record = Model.Record(cursor.getInt(cursor.getColumnIndex(KEY_ID_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_SUM_OP_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_BILL_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_TYPE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ICON_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_DELETED_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_RECORD)))

                listRecords.add(record)

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listRecords
    }

//    ---------------------------- Record Fragment ----------------------------------

    fun getAllRecordsInReverseOrder(): ArrayList<Model.Record>{

        val listRecords = ArrayList<Model.Record>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_RECORDS WHERE $KEY_DELETED_RECORD != '0' order by $KEY_ID_RECORD DESC;", null)

        if (cursor.moveToFirst()){
            do {

                record = Model.Record(cursor.getInt(cursor.getColumnIndex(KEY_ID_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_SUM_OP_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_BILL_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_TYPE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ICON_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_DELETED_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_RECORD)))

                listRecords.add(record)

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listRecords
    }

    fun getAllRecordsByBillReverseOrder(nameBill: String): ArrayList<Model.Record>{

        val listRecords = ArrayList<Model.Record>()

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_RECORDS WHERE $KEY_BILL_RECORD = '$nameBill' AND $KEY_DELETED_RECORD != '0' order by $KEY_ID_RECORD DESC;", null)

        if (cursor.moveToFirst()){
            do {

                record = Model.Record(cursor.getInt(cursor.getColumnIndex(KEY_ID_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_SUM_OP_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_BILL_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_TYPE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ICON_RECORD)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_DELETED_RECORD)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_RECORD)))

                listRecords.add(record)

            } while (cursor.moveToNext())

        }

        cursor.close()

        return listRecords

    }

//    -------------------------------------------------------------------------------

    fun getRecordById(idRecord: Int): Model.Record {

        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select * from $TABLE_RECORDS where $KEY_ID_RECORD = '$idRecord'", null)

        if (cursor.moveToFirst()){

            record = Model.Record(cursor.getInt(cursor.getColumnIndex(KEY_ID_RECORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME_RECORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_SUM_OP_RECORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_BILL_RECORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_TYPE_RECORD)),
                    cursor.getInt(cursor.getColumnIndex(KEY_COLOR_RECORD)),
                    cursor.getInt(cursor.getColumnIndex(KEY_ICON_RECORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE_RECORD)),
                    cursor.getInt(cursor.getColumnIndex(KEY_DELETED_RECORD)),
                    cursor.getInt(cursor.getColumnIndex(KEY_SYNCHRONISE_RECORD)))

        }

        cursor.close()

        return record

    }

    fun manageSynchronisationRecords(isSynchronize: Int, recordId: Long) {
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_RECORDS SET $KEY_SYNCHRONISE_RECORD = $isSynchronize WHERE $KEY_ID_RECORD = $recordId;"
        db.execSQL(sql)
    }

    fun manageDeletedStateRecords(deleted: Int, recordId: Long){
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_RECORDS SET $KEY_DELETED_RECORD = $deleted WHERE $KEY_ID_RECORD = $recordId;"
        db.execSQL(sql)
    }

    fun manageSynchronisationRecordsByBill(isSynchronize: Int, bill: String) {
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_RECORDS SET $KEY_SYNCHRONISE_RECORD = $isSynchronize WHERE $KEY_BILL_RECORD = '$bill';"
        db.execSQL(sql)
    }

    fun manageDeletedStateRecordsByBill(deleted: Int, bill: String){
        val db: SQLiteDatabase = writableDatabase
        val sql = "UPDATE $TABLE_RECORDS SET $KEY_DELETED_RECORD = $deleted WHERE $KEY_BILL_RECORD = '$bill';"
        db.execSQL(sql)
    }

    fun getRecordsCount(): Int{
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_RECORDS", null).count
    }

    fun getRecordsCountByBill(nameBill: String): Int{
        val db: SQLiteDatabase = readableDatabase
        return db.rawQuery("select * from $TABLE_RECORDS where $KEY_BILL_RECORD = '$nameBill' and $KEY_DELETED_BILL != '0'", null).count
    }

    fun getRecordId(name: String): Long{
        val db: SQLiteDatabase = readableDatabase

        val cursor = db.rawQuery("select $KEY_ID_RECORD from $TABLE_RECORDS WHERE $KEY_NAME_RECORD = '$name'", null)

        if (cursor.moveToFirst()){
            id = cursor.getLong(cursor.getColumnIndex(KEY_ID_RECORD))
        }

        cursor.close()

        return id
    }

    fun editRecord(idRecord: Long, name: String, sum: String, bill: String, type: String,
                 color: Int, icon: Int, date: String, synchronise: Int){

        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_RECORDS set $KEY_NAME_RECORD = '$name', $KEY_SUM_OP_RECORD = '$sum', $KEY_BILL_RECORD = '$bill', " +
                "$KEY_TYPE_RECORD = '$type', $KEY_COLOR_RECORD = '$color', $KEY_ICON_RECORD = '$icon', $KEY_DATE_RECORD = '$date', " +
                "$KEY_SYNCHRONISE_RECORD = '$synchronise' where $KEY_ID_RECORD = '$idRecord';"
        db.execSQL(sql)
    }

    fun updateRecordsBill(oldBillName: String, newBillName: String){

        val db: SQLiteDatabase = writableDatabase
        val sql = "update $TABLE_RECORDS set $KEY_BILL_RECORD = '$newBillName' where $KEY_BILL_RECORD = '$oldBillName';"
        db.execSQL(sql)
    }

    fun deleteRecord(recordId: Long){
        val db: SQLiteDatabase = writableDatabase
        val sql = "delete from $TABLE_RECORDS where $KEY_ID_RECORD = '$recordId';"
        db.execSQL(sql)
    }
}