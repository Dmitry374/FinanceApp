package com.example.financeapp.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by Dima on 11.05.2018.
 */

/**
 * Модуль для подтягивания навигации. В нашем случаю заюзаем Cicerone
 *  @link https://github.com/terrakok/Cicerone
 */

@Module
class NavigationModule {

    private val mCicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideNavigationRouter() = mCicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder() = mCicerone.navigatorHolder
}