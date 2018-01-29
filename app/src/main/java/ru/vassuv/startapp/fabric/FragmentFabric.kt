package ru.vassuv.startapp.fabric

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.startapp.screen.splash.SplashFragment
import ru.vassuv.startapp.screen.intro.IntroFragment
import ru.vassuv.startapp.screen.start.StartFragment
import ru.vassuv.startapp.screen.test1.Test1Fragment
import ru.vassuv.startapp.screen.test2.Test2Fragment

enum class FrmFabric(private val createFragmentLambda: (Bundle) -> MvpAppCompatFragment) {
    SPLASH({ SplashFragment.newInstance(it) }),
    INTRO({ IntroFragment.newInstance(it) }),
    START({ StartFragment.newInstance(it) }),
    EMPTY({ StartFragment.newInstance(it) }),
    TEST1({ Test1Fragment.newInstance(it) }),
    TEST2({ Test2Fragment.newInstance(it) });

    fun create(data: Bundle) = createFragmentLambda(data)
}