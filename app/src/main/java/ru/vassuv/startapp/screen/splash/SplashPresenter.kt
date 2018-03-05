package ru.vassuv.startapp.screen.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.vassuv.router.Router
import ru.vassuv.startapp.fabric.FrmFabric

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {
    private lateinit var job: Job

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        job = launch(UI) {
            (5 downTo 1).forEach {
                viewState.setText(it.toString())
                delay(500)
            }

            Router.newRootScreen(FrmFabric.INTRO.name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
