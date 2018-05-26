package com.example.financeapp.network

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

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

}