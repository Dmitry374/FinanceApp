package com.example.financeapp.ui.registration

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.network.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*

class FragmentSignIn : BasePerFragment() {

    private val TAG = FragmentRegistration::class.java.simpleName

    private var progressDialog: ProgressDialog? = null

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sign_in, container, false)

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

//        Нажатие кнопки Войти
        btnSignInOnFragmentSign.setOnClickListener {
            if (edEmailSignIn.text.toString().trim() == ""
                    || edPasswordSignIn.text.toString().trim() == ""){
                Toast.makeText(activity, getText(R.string.text_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            } else {
//                Показ ProgressDialog
                progressDialog!!.show()

//                Проверка существует ли пользователь
                signInUser(edEmailSignIn.text.toString(), edPasswordSignIn.text.toString())
            }
        }

        btnRegisterOnFragmentSignIn.setOnClickListener {
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.containerAuthRegistration, registrationFragment)
            ft.commit()
        }
    }

    private fun signInUser(email: String, password: String){

        disposable = api.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<List<Model.User>> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(user: List<Model.User>) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2: Response from server ...")
                                fragmentSignInViewModel.insertDataInLocalDB(user.get(0).name, user.get(0).surname, user.get(0).email, user.get(0).photourl, user.get(0).password, user.get(0).gender, user.get(0).datebirth, 1)
//                                Вызов рекурсивного метода или на следующее активити
                                if (fragmentSignInViewModel.getUserCount() == 0){
                                    signInUser(email, password)
                                } else {
                                    commonMethod.goNavigationScreen()
                                }

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2, HTTP Error: " + t.message)
                                Toast.makeText(activity, getText(R.string.text_not_correct_data), Toast.LENGTH_SHORT).show()
                            }
                        }
                )

    }

//    Закрытие Disposable
    override fun onDetach() {
        super.onDetach()

        if (disposable != null){
            disposable!!.dispose()
        }

    }

}