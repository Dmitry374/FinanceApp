package com.example.financeapp.network

object Model {

    data class User(var name: String, var surname: String, var email: String, var photourl: String,
                    var password: String, var gender: String, var datebirth: String, var synchronise: Int)

}