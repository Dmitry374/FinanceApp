package com.example.financeapp.di.modules

import android.content.ContentValues
import android.content.Context
import com.example.financeapp.db.DBHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль для работы с БД SQLite
 */

@Module
class DBHelperModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDatabaseHelper(contentValues: ContentValues): DBHelper {
        return DBHelper(context, contentValues)
    }

    @Provides
    @Singleton
    fun provideContentValues() = ContentValues()
}