package com.example.financeapp.network

import io.reactivex.Observable
import retrofit2.http.GET

interface CurrencyApi {

    @GET("/daily_json.js")
    fun loadCurrencyExchange(): Observable<Model.Currency>

}