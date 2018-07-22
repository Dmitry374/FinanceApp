package com.example.financeapp.sharedpreference

/**
 * Ключи для SharedPreference
 */

class SharedPreferenceConstants {
    companion object {
        const val IS_SIGN = "is_sign"
        const val REMIND_BILL_POSITION = "remind_selected_bill_position"

        const val USER_EMAIL = "user_email"
        const val USER_NAME = "user_name"
        const val USER_SURNAME = "user_surname"
        const val USER_PHOTO_URL = "user_photo_url"

        const val TEXT_CARD_BALANCE = "text_card_balance"
        const val TEXT_CARD_LAST_RECORDS = "text_card_last_records"
        const val LIMIT_CARD_RECORDS = "limit_card_records"
        const val LIMIT_CARD_RECORDS_POSITION = "limit_card_records_position"
    }
}