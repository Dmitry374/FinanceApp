package com.example.financeapp.common

class Constants {
    companion object {
        const val EMPTY_STRING = ""
        const val BASE_URL = "https://financeapp374.000webhostapp.com/"
//        const val BASE_URL = "https://ffapp.000webhostapp.com/"
        const val CURRENCY_CRB_URL = "https://www.cbr-xml-daily.ru/"

        const val RESPONSE_SUCCESS = "success"

        const val TYPE_AUTHORISATION = "typeRegAuth"
        const val REGISTER_GOOGLE = "google"
        const val REGISTER_OWN = "own"

        const val REGISTER_EMAIL = "email_from_registration"
        const val REGISTER_PASSWORD = "password_from_registration"

//        Type Operations to Network Table
        const val INSERT_ROW = "insert"
        const val UPDATE_ROW = "update"
        const val DELETE_ROW = "delete"
        const val UPDATE_AMOUNT = "update_amount"
        const val UPDATE_RECORD_BILL = "update_record_bill"
    }
}