package ru.vassuv.startapp.screen.isold.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {
//    private lateinit var job: Job
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        job = launch(UI) {
//            try {
//                Router.uiListener.showLoader()
//                val result = responseJson("https://vassuv.ru/api/v1/register/confirm/", hashMapOf(), true)
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
