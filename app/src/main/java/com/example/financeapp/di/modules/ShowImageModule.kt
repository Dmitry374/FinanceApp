package com.example.financeapp.di.modules

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dima on 10.05.2018.
 */

@Module(includes = arrayOf(ContextModule::class))
class ShowImageModule {
    @Singleton
    @Provides
    fun picasso(context: Context): Picasso {
        return Picasso.Builder(context)
                .build()
    }
}