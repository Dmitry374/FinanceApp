package com.example.financeapp.network

import io.reactivex.Observable
import retrofit2.Call
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
    ): Observable<List<Model.User>>

}