package com.example.financeapp.ui.registration

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.base.BaseFragment
import com.example.financeapp.common.Constants.Companion.REGISTER_OWN
import com.example.financeapp.common.Constants.Companion.RESPONSE_SUCCESS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_registration.*

class FragmentRegistration : BaseFragment() {

    private val TAG = FragmentRegistration::class.java.simpleName

    private var progressDialog: ProgressDialog? = null

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_registration, container, false)

//        Имя Toolbar
        activity.title = getText(R.string.text_registration)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Инициализация ProgressDialog
        progressDialog = ProgressDialog(activity)
        progressDialog!!.max = 100
        progressDialog!!.setMessage(resources.getText(R.string.text_progress_dialog))
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setCanceledOnTouchOutside(false)

//        Нажатие кнопки Зарегистрироваться
        btnRegisterOnFragmentReg.setOnClickListener {
            if (edEmailRegister.text.toString().trim() == ""
                    || edPasswordRegister.text.toString().trim() == ""){
                Toast.makeText(activity, getText(R.string.text_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            } else if (edPasswordRegister.text.toString().trim().length < 6) {
                Toast.makeText(activity, getText(R.string.error_length_password), Toast.LENGTH_SHORT).show()
            } else {
//                Показ ProgressDialog
                progressDialog!!.show()

//                Проверка существует ли пользователь
                checkUserByEmail(edEmailRegister.text.toString())
            }

        }

//        Нажатие кнопки Войти
        btnSignInOnFragmentReg.setOnClickListener {
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.containerAuthRegistration, signInFragment)
            ft.commit()
        }
    }

//    Проверка существует ли пользователь
    private fun checkUserByEmail(email: String){
        disposable = api.checkUserExist(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<String> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: String) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2: Response from server ...")

                                if (response == RESPONSE_SUCCESS){
                                    commonMethod.goSyncScreen(REGISTER_OWN, edEmailRegister.text.toString(), edPasswordRegister.text.toString())
                                } else {
                                    Toast.makeText(activity, getText(R.string.text_user_already_exists), Toast.LENGTH_SHORT).show()
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2, HTTP Error: " + t.message)
                                Toast.makeText(activity, getText(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                            }
                        }
                )
    }

    override fun onDetach() {
        super.onDetach()

        if (disposable != null){
            disposable!!.dispose()
        }
    }

}