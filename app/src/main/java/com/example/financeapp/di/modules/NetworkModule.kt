package com.example.financeapp.di.modules

import android.app.ProgressDialog
import android.content.Context
import com.example.financeapp.common.CommonMethod
import com.example.financeapp.common.Constants.Companion.BASE_URL
import com.example.financeapp.db.DBHelper
import com.example.financeapp.network.Api
import com.example.financeapp.network.NetworkHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(ContextModule::class))
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideNetworkHelper(commonMethod: CommonMethod, api: Api, dbHelper: DBHelper) =
            NetworkHelper(context, commonMethod, api, dbHelper)

    @Provides
    @Singleton
    fun serverApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        return client.build()
    }

}