package com.example.financeapp.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.DELETE_ROW
import com.example.financeapp.common.Constants.Companion.INSERT_ROW
import com.example.financeapp.common.Constants.Companion.RESPONSE_SUCCESS
import com.example.financeapp.common.Constants.Companion.UPDATE_AMOUNT
import com.example.financeapp.common.Constants.Companion.UPDATE_RECORD_BILL
import com.example.financeapp.common.Constants.Companion.UPDATE_ROW
import com.example.financeapp.db.DBHelper
import com.example.financeapp.sharedpreference.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/*
* Класс для работы с сетевыми запросами
* */

class NetworkHelper(private val context: Context,
                    private val commonMethod: CommonMethod,
                    private val api: Api,
                    private val sharedPreferenceHelper: SharedPreferenceHelper,
                    private val dbHelper: DBHelper) {

    lateinit var disposable: Disposable

    fun loadUserDataOnServer(name: String, surname: String, email: String, photoUrl: String, password: String,
                             gender: String, dateOfBirth: String){

        disposable = api.registerUser(name, surname, email, photoUrl, password, gender, dateOfBirth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<List<Model.User>> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(userList: List<Model.User>) {
                                Log.i("myLogs", "RxJava2: Response from server ...")
//                                        Log.d("myLogs", "FROM SERVER name = ${userList.get(0).name} surname = ${userList.get(0).surname} email = ${userList.get(0).email} photoUrl = ${userList.get(0).photourl}  dateOfBirth = ${userList.get(0).datebirth}")
                                dbHelper.insertInfAboutUser(userList.get(0).name, userList.get(0).surname, userList.get(0).email, userList.get(0).photourl, userList.get(0).password, userList.get(0).gender, userList.get(0).datebirth, 1)

                                Log.d("myLogs", "NetworkHelper UserCount = ${dbHelper.getUserCount()}")

//                                        Вызов рекурсивного метода или на следующее активити
                                val count = dbHelper.getUserCount()
                                if (dbHelper.getUserCount() == 0){
                                    loadUserDataOnServer(name, surname, email, photoUrl, password, gender, dateOfBirth)
                                } else {
                                    getBillTable()
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "RxJava2, HTTP Error: " + t.message)
                            }
                        }
                )

    }

//    ------------------------ Bill Table -----------------------------

    fun addNewBill(name: String, amount: String, currency: String, color: Int, colorPosition: Int, isSynchronize: Int) {

//        Добавить новый счет и получить ID записи
        val idBill = dbHelper.addNewBill(name, amount, currency, color, colorPosition, 1, isSynchronize)

        disposable = api.billTable(INSERT_ROW, name, amount, currency, color, colorPosition, sharedPreferenceHelper.getUserEmail(), idBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", " addNewBill RxJava2: Response from server ... response = $response")


                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationBill(1, idBill)
                                } else {
//                                    Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationBill(0, idBill)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "addNewBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationBill(0, idBill)
                            }
                        }
                )

    }

    fun updateBill(oldBillName: String, newBillName: String, color: Int, colorPosition: Int){

        val idBill = dbHelper.getBillId(oldBillName)  // ID изменяемого счета

        dbHelper.editBill(oldBillName, newBillName, color, colorPosition, 0)

        disposable = api.billTable(UPDATE_ROW, newBillName, "", "", color, colorPosition, sharedPreferenceHelper.getUserEmail(), idBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "updateBill RxJava2: Response from server ... response = $response")


                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationBill(1, idBill)
                                } else {
//                                Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationBill(0, idBill)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "updateBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationBill(0, idBill)
                            }
                        }
                )

    }

    fun deleteBill(billName: String){
        val idBill = dbHelper.getBillId(billName)  // ID удаляемого счета

//        Помечаем флагом что удален, при синхронизации удаляются все поля со значением "0" в ст. "deleted"
        dbHelper.manageDeletedState(0, idBill)

        disposable = api.billTable(DELETE_ROW, "", "", "", 0, 0, sharedPreferenceHelper.getUserEmail(), idBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "deleteBill RxJava2: Response from server ... response = $response")

                                if (response == RESPONSE_SUCCESS){
                                    dbHelper.deleteBill(idBill)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "deleteBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageDeletedState(0, idBill)
                            }
                        }
                )


    }

    fun updateAmountBill(sumAmountBill: String, billName: String){

        val idBill = dbHelper.getBillId(billName)

        dbHelper.updateAmountBill(sumAmountBill, billName)

        disposable = api.billTable(UPDATE_AMOUNT, "", sumAmountBill, "", 0, 0, sharedPreferenceHelper.getUserEmail(), idBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "updateAmountBill RxJava2: Response from server ... response = $response")

                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationBill(1, idBill)
                                } else {
//                                Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationBill(0, idBill)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "updateAmountBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationBill(0, idBill)
                            }
                        }
                )

    }

    fun getBillTable(){

        disposable = api.getBillTable(sharedPreferenceHelper.getUserEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<List<Model.Bill>> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(listBill: List<Model.Bill>) {

                                Log.d("myLog", "getBillTable getUserEmail = ${sharedPreferenceHelper.getUserEmail()}")

                                // Заполнение таблицы счетов с сервера
                                for (item in listBill){
                                    dbHelper.addNewBill(item.name, item.amount, item.currency, item.color, item.color_position, 1, 1)
                                }

                                getRecordsTable()

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "getBillTable RxJava2, HTTP Error: " + t.message)
//                                Toast.makeText(context, context.getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                commonMethod.goNavigationScreen()
                            }
                        }
                )

    }

//    ------------------------ Records ---------------------

    fun getRecordsTable(){

        disposable = api.getRecordsTable(sharedPreferenceHelper.getUserEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<List<Model.Record>> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(listRecords: List<Model.Record>) {

                                Log.d("myLog", "getRecordsTable getUserEmail = ${sharedPreferenceHelper.getUserEmail()}")

                                // Заполнение таблицы счетов с сервера
                                for (item in listRecords){
                                    dbHelper.addNewRecord(item.name, item.sum, item.bill, item.type, item.color, item.icon, item.date, 1, 1)
                                }

                                commonMethod.goNavigationScreen()

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "getRecordsTable RxJava2, HTTP Error: " + t.message)
//                                Toast.makeText(context, context.getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                commonMethod.goNavigationScreen()
                            }
                        }
                )

    }


    fun addNewRecord(name: String, sum: String, bill: String, type: String,
                     color: Int, icon: Int, date: String, isSynchronise: Int) {

//        Добавить новый счет и получить ID записи
        val idRecord = dbHelper.addNewRecord(name, sum, bill, type, color, icon, date, 1, isSynchronise)

        disposable = api.recordsTable(INSERT_ROW, name, sum, bill, type, color, icon, date, sharedPreferenceHelper.getUserEmail(), idRecord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", " addNewRecord RxJava2: Response from server ... response = $response")


                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationRecords(1, idRecord)
                                } else {
//                                    Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationRecords(0, idRecord)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "addNewRecord RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationRecords(0, idRecord)
                            }
                        }
                )

    }

    fun updateRecord(idRecord: Long, name: String, sum: String, bill: String, type: String, color: Int, icon: Int, date: String){

//        val idRecord = dbHelper.getRecordId(name)  // ID изменяемого счета

        dbHelper.editRecord(idRecord, name, sum, bill, type, color, icon, date, 0)

        disposable = api.recordsTable(UPDATE_ROW, name, sum, bill, type, color, icon, date, sharedPreferenceHelper.getUserEmail(), idRecord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "updateRecord RxJava2: Response from server ... response = $response")


                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationRecords(1, idRecord)
                                } else {
//                                Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationRecords(0, idRecord)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "updateRecord RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationRecords(0, idRecord)
                            }
                        }
                )

    }

    fun updateRecordsBill(oldBillName: String, newBillName: String){

        dbHelper.updateRecordsBill(oldBillName, newBillName)

        disposable = api.recordsTable(UPDATE_RECORD_BILL, oldBillName, "", newBillName, "", 0, 0, "", sharedPreferenceHelper.getUserEmail(), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "updateRecordsBill RxJava2: Response from server ... response = $response")


                                if (response == RESPONSE_SUCCESS){
//                                    Отметим что Синхронизировано
                                    dbHelper.manageSynchronisationRecordsByBill(1, newBillName)
                                } else {
//                                Отметим что НЕ Синхронизировано
                                    dbHelper.manageSynchronisationRecordsByBill(0, newBillName)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "updateRecordsBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageSynchronisationRecordsByBill(0, newBillName)
                            }
                        }
                )

    }

    fun deleteRecord(idRecord: Long){

//        Помечаем флагом что удален, при синхронизации удаляются все поля со значением "0" в ст. "deleted"
        dbHelper.manageDeletedStateRecords(0, idRecord)

        disposable = api.recordsTable(DELETE_ROW, "", "", "", "", 0, 0, "", sharedPreferenceHelper.getUserEmail(), idRecord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                Log.i("myLogs", "deleteBill RxJava2: Response from server ... response = $response")

                                if (response == RESPONSE_SUCCESS){
                                    dbHelper.deleteRecord(idRecord)
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                Log.i("myLogs", "deleteBill RxJava2, HTTP Error: " + t.message)
//                                Отметим что НЕ Синхронизировано
                                dbHelper.manageDeletedStateRecords(0, idRecord)
                            }
                        }
                )
    }



    fun closeDisposable(){
        disposable.dispose()

    }

}