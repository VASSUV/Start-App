package ru.vassuv.startapp.fabric

import android.os.Bundle
import ru.vassuv.startapp.screen.intro.IntroFragment
import ru.vassuv.startapp.screen.login.LoginFragment
import ru.vassuv.startapp.screen.splash.SplashFragment
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import kotlin.reflect.KClass

enum class FrmFabric(private val kClass: KClass<out BaseFragment>) {
    SPLASH(SplashFragment::class),
    INTRO(IntroFragment::class),
    LOGIN(LoginFragment::class),

    EMPTY(SplashFragment::class);

    fun create(data: Bundle): BaseFragment {
        val fragment = kClass.java.constructors[0].newInstance() as BaseFragment
        fragment.arguments = data
        return fragment
    }

    companion object {
        @JvmStatic
        fun valueOf(kClass: KClass<out BaseFragment>): FrmFabric {
            values().forEach {
                if(it.kClass == kClass) return it
            }
            return EMPTY
        }
    }
}