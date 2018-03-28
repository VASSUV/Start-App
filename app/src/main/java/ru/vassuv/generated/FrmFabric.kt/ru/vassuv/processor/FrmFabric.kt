// """
// |\*
// |                        ********************************
// |                        *      Генерируемый файл       *
// |                        ********************************
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        *                              *
// |                        ********************************
// |                    *\
// """.trimMargin()
package ru.vassuv.processor

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlin.String
import kotlin.jvm.JvmStatic
import ru.vassuv.startapp.screen.intro.IntroFragment
import ru.vassuv.startapp.screen.isold.start.StartFragment
import ru.vassuv.startapp.screen.isold.test1.Test1Fragment
import ru.vassuv.startapp.screen.isold.test2.Test2Fragment
import ru.vassuv.startapp.screen.login.LoginFragment
import ru.vassuv.startapp.screen.splash.SplashFragment
import ru.vassuv.startapp.screen.test3.Test3Fragment

object FrmFabric {
    val TEST_3: String = "TEST_3" // Test3Fragment

    val TEST_1: String = "TEST_1" // Test1Fragment

    val TEST_2: String = "TEST_2" // Test2Fragment

    val START: String = "START" // StartFragment

    val SPLASH: String = "SPLASH" // SplashFragment

    val INTRO: String = "INTRO" // IntroFragment

    val LOGIN: String = "LOGIN" // LoginFragment

    @JvmStatic
    private fun createTest3Fragment(args: Bundle? = null): Test3Fragment {
        val fragment = Test3Fragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createTest1Fragment(args: Bundle? = null): Test1Fragment {
        val fragment = Test1Fragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createTest2Fragment(args: Bundle? = null): Test2Fragment {
        val fragment = Test2Fragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createStartFragment(args: Bundle? = null): StartFragment {
        val fragment = StartFragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createSplashFragment(args: Bundle? = null): SplashFragment {
        val fragment = SplashFragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createIntroFragment(args: Bundle? = null): IntroFragment {
        val fragment = IntroFragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    private fun createLoginFragment(args: Bundle? = null): LoginFragment {
        val fragment = LoginFragment()
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    fun createFragment(fragmentName: String, args: Bundle? = null): Fragment {
        val fragment: Fragment = when(fragmentName) {
            TEST_3 -> Test3Fragment()
            TEST_1 -> Test1Fragment()
            TEST_2 -> Test2Fragment()
            START -> StartFragment()
            SPLASH -> SplashFragment()
            INTRO -> IntroFragment()
            LOGIN -> LoginFragment()
            else -> throw Exception("Передан не аннатируемый фрагмент")
        }
        fragment.arguments = args
        return fragment
    }

    @JvmStatic
    fun valueOf(fragment: Fragment): String {
        val name: String = when(fragment::class) {
            Test3Fragment::class -> TEST_3
            Test1Fragment::class -> TEST_1
            Test2Fragment::class -> TEST_2
            StartFragment::class -> START
            SplashFragment::class -> SPLASH
            IntroFragment::class -> INTRO
            LoginFragment::class -> LOGIN
            else -> ""
        }
        return name
    }
}
