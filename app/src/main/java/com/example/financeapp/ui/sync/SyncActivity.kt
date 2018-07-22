package com.example.financeapp.ui.sync

import android.os.Bundle
import com.example.financeapp.R
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.common.Constants.Companion.EMPTY_STRING
import com.example.financeapp.common.Constants.Companion.REGISTER_EMAIL
import com.example.financeapp.common.Constants.Companion.REGISTER_GOOGLE
import com.example.financeapp.common.Constants.Companion.REGISTER_PASSWORD
import com.example.financeapp.common.Constants.Companion.TYPE_AUTHORISATION
import com.google.android.gms.common.api.GoogleApiClient

class SyncActivity : GoogleApiClientBaseActivity(), GoogleApiClient.OnConnectionFailedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        if (intent.getStringExtra(TYPE_AUTHORISATION) == REGISTER_GOOGLE){   //  Google
            syncActivityViewModel.loadDataFromGoogleApiToDB(mGoogleApiClient)
        } else {                                                            // Own
            syncActivityViewModel.loadUserDataOnServer(intent.getStringExtra(REGISTER_EMAIL),
                    EMPTY_STRING, intent.getStringExtra(REGISTER_EMAIL),
                    EMPTY_STRING, intent.getStringExtra(REGISTER_PASSWORD), EMPTY_STRING, EMPTY_STRING)
        }

        /* ----------------------------------------------------------------------------- */
//        disposable = api.registerUser("Dima", "Tr", "dima@yandex.ru", "url",
//                "password", "male", "11.07")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        object : Consumer<List<Model.User>> {
//                            @SuppressLint("SetTextI18n")
//                            @Throws(Exception::class)
//                            override fun accept(userList: List<Model.User>) {
//                                Log.i("myLogs", "RxJava2: Response from server ...")
//                                Toast.makeText(this@SyncActivity, userList.get(0).name, Toast.LENGTH_SHORT).show()
//                                Log.d("myLogs", "photoUrl = ${userList.get(0).photourl}  dateOfBirth = ${userList.get(0).photourl}")
//                                dbHelper.insertInfAboutUser(userList.get(0).name, userList.get(0).surname, userList.get(0).email, userList.get(0).photourl, userList.get(0).password, userList.get(0).gender, userList.get(0).datebirth, 1)
//
//                            }
//                        },
//                        object : Consumer<Throwable> {
//                            @Throws(Exception::class)
//                            override fun accept(t: Throwable) {
//                                Log.i("myLogs", "RxJava2, HTTP Error: " + t.message)
//                            }
//                        }
//                )
        /* ----------------------------------------------------------------------------- */


    }

//    SyncActivityViewModel записывает данные с сервера локально и осуществляет переход на NavigationActivity
    override fun onPause() {
        super.onPause()
        finish()
    }

//    Закрытие Disposable
    override fun onDestroy() {
        super.onDestroy()
        syncActivityViewModel.closeDisposable()
    }
}
