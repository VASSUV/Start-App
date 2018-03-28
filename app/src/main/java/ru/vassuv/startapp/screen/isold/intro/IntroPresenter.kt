package ru.vassuv.startapp.screen.isold.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.experimental.Job

@InjectViewState
class IntroPresenter : MvpPresenter<IntroView>() {
    private lateinit var job: Job

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        job = auth(viewState)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
