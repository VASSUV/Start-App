package ru.vassuv.startapp.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.vassuv.startapp.R

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

class SplashFragment : BaseFragment(), SplashView {
    override val type = FrmFabric.SPLASH

    companion object {
        fun newInstance(args: Bundle): SplashFragment {
            val fragment = SplashFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private lateinit var job: Job

    override fun onStart() {
        super.onStart()
        val progress = activity.findViewById<ProgressBar>(R.id.progress)

        job = launch(UI) {
            progress.visibility = View.VISIBLE
            delay(1)
            try {
                withContext(CommonPool) {
                    Thread.sleep(5000)
                }
            } finally {
                progress.visibility = View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        launch(UI) {
            job.cancelChildren()
            job.cancel()
        }
    }
}
