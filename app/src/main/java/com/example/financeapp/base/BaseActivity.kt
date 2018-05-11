package com.example.financeapp.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.example.financeapp.FragmentMain
import com.example.financeapp.FragmentSecond
import com.example.financeapp.NavigationScreenKey.Companion.EXTRA_NEXT_FRAGMENT
import com.example.financeapp.NavigationScreenKey.Companion.EXTRA_NEXT_FRAGMENT_ARGS
import com.example.financeapp.NavigationScreenKey.Companion.GRAPH
import com.example.financeapp.NavigationScreenKey.Companion.MAIN_FRAGMENT
import com.example.financeapp.NavigationScreenKey.Companion.SECOND_FRAGMENT
import com.example.financeapp.R
import dagger.android.AndroidInjection
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 * Базовая активити
 */

 abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var mNavigationHolder: NavigatorHolder

    @Inject
    lateinit var mRouter: Router

    companion object {
        private const val STOP_INSTANCE_CREATION_CYCLE_FLAG = "stop here!!!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        // возможно были переданы аргументы для создания конкретного фрагмента с параметрами
        var openFragment = getDefaultFragment()
        intent?.let { intent ->
            (intent.getStringExtra(EXTRA_NEXT_FRAGMENT))?.let {
                openFragment = it
            }
        }
        if (!TextUtils.isEmpty(openFragment)) {
            intent.putExtra(STOP_INSTANCE_CREATION_CYCLE_FLAG, true)
            mRouter.newRootScreen(openFragment, intent?.getParcelableExtra(EXTRA_NEXT_FRAGMENT_ARGS))
        }

        super.onCreate(savedInstanceState)
    }

    private val mBaseNavigator by lazy {
        object : SupportAppNavigator(this, getFragmentContainerResId()) {

            override fun createActivityIntent(screenKey: String?, data: Any?): Intent? {
                // если желаемый фрагмент не может быть создан в этой активити
                // создадим intent нужной активити, и в качестве параметров
                // запишем желаемый фрагмент, плюс упакуем желаемые аргументы
                GRAPH.filter { it.key != this@BaseActivity::class.java && it.value.contains(screenKey) }.keys.firstOrNull()?.let {
                    return Intent(this@BaseActivity, it).apply {
                        putExtra(EXTRA_NEXT_FRAGMENT, screenKey)
                        putExtra(EXTRA_NEXT_FRAGMENT_ARGS, data as? Bundle?)
                    }
                }

                // цикл прерван, теперь навигатор сможет создавать инстансы для _других_ экранов
                intent.removeExtra(STOP_INSTANCE_CREATION_CYCLE_FLAG)
                return null
            }

            override fun createFragment(screenKey: String?, data: Any?) =
                    when (screenKey) {
                        MAIN_FRAGMENT -> FragmentMain().apply { arguments = data as? Bundle }
                        SECOND_FRAGMENT -> FragmentSecond().apply { arguments = data as? Bundle }
                        else -> FragmentMain().apply { arguments = data as? Bundle }
                    }

            override fun showSystemMessage(message: String?) = Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    open fun getDefaultFragment() = ""

    open fun getFragmentContainerResId() = R.id.fragment_container

    override fun onResume() {
        super.onResume()
        mNavigationHolder.setNavigator(mBaseNavigator)
    }

    override fun onPause() {
        super.onPause()
        mNavigationHolder.removeNavigator()
    }

 }