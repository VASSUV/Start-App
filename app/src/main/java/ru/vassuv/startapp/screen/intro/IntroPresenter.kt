package ru.vassuv.startapp.screen.intro

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.vassuv.startapp.utils.Router
import ru.vassuv.startapp.utils.atlibrary.responseJson

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
