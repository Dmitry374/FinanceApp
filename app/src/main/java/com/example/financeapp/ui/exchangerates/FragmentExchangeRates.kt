package com.example.financeapp.ui.exchangerates

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.financeapp.R
import com.example.financeapp.adapter.RecyclerRatesAdapter
import com.example.financeapp.base.BasePerFragment
import com.example.financeapp.common.Constants
import com.example.financeapp.network.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_exchange_rates.*
import kotlinx.android.synthetic.main.fragment_records.*

class FragmentExchangeRates : BasePerFragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var progressDialog: ProgressDialog? = null

    lateinit var currency: Model.Currency
    lateinit var valute: Model.Valute

    lateinit var listValuta: ArrayList<Model.Valuta>

    private val TAG = FragmentExchangeRates::class.java.simpleName

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_exchange_rates, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listValuta = ArrayList<Model.Valuta>()

//        Инициализация ProgressDialog
        progressDialog = ProgressDialog(activity)
        progressDialog!!.max = 100
        progressDialog!!.setMessage(resources.getText(R.string.text_progress_dialog))
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setCanceledOnTouchOutside(false)

        progressDialog!!.show()

        disposable = api.getExchangeRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Consumer<Model.Currency> {
                            @SuppressLint("SetTextI18n")
                            @Throws(Exception::class)
                            override fun accept(response: Model.Currency) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2: Response from server ...")

                                currency = response

                                valute = response.Valute

                                listValuta.add(Model.Valuta(valute.AUD.ID, valute.AUD.NumCode, valute.AUD.CharCode, valute.AUD.Nominal, valute.AUD.Name, valute.AUD.Value, valute.AUD.Previous))
                                listValuta.add(Model.Valuta(valute.AZN.ID, valute.AZN.NumCode, valute.AZN.CharCode, valute.AZN.Nominal, valute.AZN.Name, valute.AZN.Value, valute.AZN.Previous))
                                listValuta.add(Model.Valuta(valute.GBP.ID, valute.GBP.NumCode, valute.GBP.CharCode, valute.GBP.Nominal, valute.GBP.Name, valute.GBP.Value, valute.GBP.Previous))
                                listValuta.add(Model.Valuta(valute.AMD.ID, valute.AMD.NumCode, valute.AMD.CharCode, valute.AMD.Nominal, valute.AMD.Name, valute.AMD.Value, valute.AMD.Previous))
                                listValuta.add(Model.Valuta(valute.BYN.ID, valute.BYN.NumCode, valute.BYN.CharCode, valute.BYN.Nominal, valute.BYN.Name, valute.BYN.Value, valute.BYN.Previous))
                                listValuta.add(Model.Valuta(valute.BGN.ID, valute.BGN.NumCode, valute.BGN.CharCode, valute.BGN.Nominal, valute.BGN.Name, valute.BGN.Value, valute.BGN.Previous))
                                listValuta.add(Model.Valuta(valute.BRL.ID, valute.BRL.NumCode, valute.BRL.CharCode, valute.BRL.Nominal, valute.BRL.Name, valute.BRL.Value, valute.BRL.Previous))
                                listValuta.add(Model.Valuta(valute.HUF.ID, valute.HUF.NumCode, valute.HUF.CharCode, valute.HUF.Nominal, valute.HUF.Name, valute.HUF.Value, valute.HUF.Previous))
                                listValuta.add(Model.Valuta(valute.HKD.ID, valute.HKD.NumCode, valute.HKD.CharCode, valute.HKD.Nominal, valute.HKD.Name, valute.HKD.Value, valute.HKD.Previous))
                                listValuta.add(Model.Valuta(valute.DKK.ID, valute.DKK.NumCode, valute.DKK.CharCode, valute.DKK.Nominal, valute.DKK.Name, valute.DKK.Value, valute.DKK.Previous))
                                listValuta.add(Model.Valuta(valute.USD.ID, valute.USD.NumCode, valute.USD.CharCode, valute.USD.Nominal, valute.USD.Name, valute.USD.Value, valute.USD.Previous))
                                listValuta.add(Model.Valuta(valute.EUR.ID, valute.EUR.NumCode, valute.EUR.CharCode, valute.EUR.Nominal, valute.EUR.Name, valute.EUR.Value, valute.EUR.Previous))
                                listValuta.add(Model.Valuta(valute.INR.ID, valute.INR.NumCode, valute.INR.CharCode, valute.INR.Nominal, valute.INR.Name, valute.INR.Value, valute.INR.Previous))
                                listValuta.add(Model.Valuta(valute.KZT.ID, valute.KZT.NumCode, valute.KZT.CharCode, valute.KZT.Nominal, valute.KZT.Name, valute.KZT.Value, valute.KZT.Previous))
                                listValuta.add(Model.Valuta(valute.CAD.ID, valute.CAD.NumCode, valute.CAD.CharCode, valute.CAD.Nominal, valute.CAD.Name, valute.CAD.Value, valute.CAD.Previous))
                                listValuta.add(Model.Valuta(valute.KGS.ID, valute.KGS.NumCode, valute.KGS.CharCode, valute.KGS.Nominal, valute.KGS.Name, valute.KGS.Value, valute.KGS.Previous))
                                listValuta.add(Model.Valuta(valute.CNY.ID, valute.CNY.NumCode, valute.CNY.CharCode, valute.CNY.Nominal, valute.CNY.Name, valute.CNY.Value, valute.CNY.Previous))
                                listValuta.add(Model.Valuta(valute.MDL.ID, valute.MDL.NumCode, valute.MDL.CharCode, valute.MDL.Nominal, valute.MDL.Name, valute.MDL.Value, valute.MDL.Previous))
                                listValuta.add(Model.Valuta(valute.NOK.ID, valute.NOK.NumCode, valute.NOK.CharCode, valute.NOK.Nominal, valute.NOK.Name, valute.NOK.Value, valute.NOK.Previous))
                                listValuta.add(Model.Valuta(valute.PLN.ID, valute.PLN.NumCode, valute.PLN.CharCode, valute.PLN.Nominal, valute.PLN.Name, valute.PLN.Value, valute.PLN.Previous))
                                listValuta.add(Model.Valuta(valute.RON.ID, valute.RON.NumCode, valute.RON.CharCode, valute.RON.Nominal, valute.RON.Name, valute.RON.Value, valute.RON.Previous))
                                listValuta.add(Model.Valuta(valute.XDR.ID, valute.XDR.NumCode, valute.XDR.CharCode, valute.XDR.Nominal, valute.XDR.Name, valute.XDR.Value, valute.XDR.Previous))
                                listValuta.add(Model.Valuta(valute.SGD.ID, valute.SGD.NumCode, valute.SGD.CharCode, valute.SGD.Nominal, valute.SGD.Name, valute.SGD.Value, valute.SGD.Previous))
                                listValuta.add(Model.Valuta(valute.TJS.ID, valute.TJS.NumCode, valute.TJS.CharCode, valute.TJS.Nominal, valute.TJS.Name, valute.TJS.Value, valute.TJS.Previous))
                                listValuta.add(Model.Valuta(valute.TRY.ID, valute.TRY.NumCode, valute.TRY.CharCode, valute.TRY.Nominal, valute.TRY.Name, valute.TRY.Value, valute.TRY.Previous))
                                listValuta.add(Model.Valuta(valute.TMT.ID, valute.TMT.NumCode, valute.TMT.CharCode, valute.TMT.Nominal, valute.TMT.Name, valute.TMT.Value, valute.TMT.Previous))
                                listValuta.add(Model.Valuta(valute.UZS.ID, valute.UZS.NumCode, valute.UZS.CharCode, valute.UZS.Nominal, valute.UZS.Name, valute.UZS.Value, valute.UZS.Previous))
                                listValuta.add(Model.Valuta(valute.UAH.ID, valute.UAH.NumCode, valute.UAH.CharCode, valute.UAH.Nominal, valute.UAH.Name, valute.UAH.Value, valute.UAH.Previous))
                                listValuta.add(Model.Valuta(valute.CZK.ID, valute.CZK.NumCode, valute.CZK.CharCode, valute.CZK.Nominal, valute.CZK.Name, valute.CZK.Value, valute.CZK.Previous))
                                listValuta.add(Model.Valuta(valute.SEK.ID, valute.SEK.NumCode, valute.SEK.CharCode, valute.SEK.Nominal, valute.SEK.Name, valute.SEK.Value, valute.SEK.Previous))
                                listValuta.add(Model.Valuta(valute.CHF.ID, valute.CHF.NumCode, valute.CHF.CharCode, valute.CHF.Nominal, valute.CHF.Name, valute.CHF.Value, valute.CHF.Previous))
                                listValuta.add(Model.Valuta(valute.ZAR.ID, valute.ZAR.NumCode, valute.ZAR.CharCode, valute.ZAR.Nominal, valute.ZAR.Name, valute.ZAR.Value, valute.ZAR.Previous))
                                listValuta.add(Model.Valuta(valute.KRW.ID, valute.KRW.NumCode, valute.KRW.CharCode, valute.KRW.Nominal, valute.KRW.Name, valute.KRW.Value, valute.KRW.Previous))
                                listValuta.add(Model.Valuta(valute.JPY.ID, valute.JPY.NumCode, valute.JPY.CharCode, valute.JPY.Nominal, valute.JPY.Name, valute.JPY.Value, valute.JPY.Previous))

                                linearLayoutManager = LinearLayoutManager(activity)
                                recyclerExchangeRates.layoutManager = linearLayoutManager

                                recyclerExchangeRates.adapter = RecyclerRatesAdapter(activity, picasso, listValuta, currency, object : RecyclerRatesAdapter.OnItemClickListener {
                                    override fun onItemClick(valute: Model.Valuta, position: Int) {



                                    }

                                })

                            }
                        },
                        object : Consumer<Throwable> {
                            @Throws(Exception::class)
                            override fun accept(t: Throwable) {
                                progressDialog!!.dismiss()
                                Log.i("myLogs", "$TAG RxJava2, HTTP Error: " + t.message)
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