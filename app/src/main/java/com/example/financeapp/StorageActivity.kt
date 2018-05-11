package com.example.financeapp

import android.os.Bundle
import com.example.financeapp.base.BaseActivity
import dagger.android.AndroidInjection

/**
 * Базовая активити работающая с хранилищем и запросами к API
 */

open class StorageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}