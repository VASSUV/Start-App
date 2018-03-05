package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class IntroPresenter : MvpPresenter<IntroView>() {
//    private lateinit var job: Job
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        job = launch(UI) {
//            try {
//                Router.uiListener.showLoader()
//                val result = responseJson("https://vassuv.ru/api/v1/login/", hashMapOf(), true)
//                viewState.setText(result.toString())
//                if(result.error.status > 0)
//                    Router.uiListener.showMessage(result.error.message ?: "")
//            } finally {
//                Router.uiListener.hideLoader()
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        job.cancel()
//    }
}
