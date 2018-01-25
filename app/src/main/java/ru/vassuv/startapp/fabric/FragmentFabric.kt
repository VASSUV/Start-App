package ru.vassuv.startapp.fabric

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.vassuv.startapp.screen.splash.SplashFragment
import ru.vassuv.startapp.screen.intro.IntroFragment
import ru.vassuv.startapp.screen.start.StartFragment

enum class FrmFabric(private val createFragmentLambda: (Bundle) -> MvpAppCompatFragment) {
    SPLASH({ SplashFragment.newInstance(it) }),
    INTRO({ IntroFragment.newInstance(it) }),
    START({ StartFragment.newInstance(it) }),
    EMPTY({ StartFragment.newInstance(it) });

    fun create(data: Bundle) = createFragmentLambda(data)
}