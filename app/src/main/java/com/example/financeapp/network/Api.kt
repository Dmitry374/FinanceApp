package com.example.financeapp.network

import io.reactivex.Observable
import retrofit2.http.*

interface Api {

//    Регистрация пользователя
    @FormUrlEncoded
    @POST("/register_user.php/")
    fun registerUser(
            @Field("name") name: String,
            @Field("surname") surname: String,
            @Field("email") email: String,
            @Field("photo_url") photoUrl: String,
            @Field("password") password: String,
            @Field("gender") gender: String,
            @Field("date_of_birth") dateOfBirth: String
    ): Observable<List<Model.User>>

//    Проверка существует ли ползователь
    @GET("/check_user.php/")
    fun checkUserExist(
            @Query("email") email: String
    ): Observable<String>

//    Вход по email и password
    @FormUrlEncoded
    @POST("/login_user.php/")
    fun loginUser(
            @Field("email") email: String,
            @Field("password") password: String
    ): Observable<String>

    @FormUrlEncoded
    @POST("/bill_table.php/")
    fun billTable(
            @Field("action_type") actionType: String,
            @Field("name") name: String,
            @Field("amount") amount: String,
            @Field("currency") currency: String,
            @Field("color") color: Int,
            @Field("color_position") colorPosition: Int,
            @Field("user_email") userEmail: String,
            @Field("local_id") localId: Long
    ): Observable<String>

    @FormUrlEncoded
    @POST("/get_bill_table.php/")
    fun getBillTable(
            @Field("user_email") userEmail: String
    ): Observable<List<Model.Bill>>

    @FormUrlEncoded
    @POST("/get_records_table.php/")
    fun getRecordsTable(
            @Field("user_email") userEmail: String
    ): Observable<List<Model.Record>>

    @FormUrlEncoded
    @POST("/records_table.php/")
    fun recordsTable(
            @Field("action_type") actionType: String,
            @Field("name") name: String,
            @Field("sum") sum: String,
            @Field("bill") bill: String,
            @Field("type") type: String,
            @Field("color") color: Int,
            @Field("icon") icon: Int,
            @Field("date") date: String,
            @Field("user_email") userEmail: String,
            @Field("local_id") localId: Long
    ): Observable<String>

    @GET("/exchange_rates.php/")
    fun getExchangeRates(): Observable<Model.Currency>

//    Обновление данных пользователя
    @FormUrlEncoded
    @POST("/update_user_table.php/")
    fun updateUser(
            @Field("name") name: String,
            @Field("surname") surname: String,
            @Field("email") email: String,
            @Field("photo_url") photoUrl: String,
            @Field("password") password: String,
            @Field("gender") gender: String,
            @Field("date_of_birth") dateOfBirth: String
    ): Observable<String>
}