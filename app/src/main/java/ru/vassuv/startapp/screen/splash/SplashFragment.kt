package ru.vassuv.startapp.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.vassuv.startapp.R

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import ru.vassuv.startapp.utils.atlibrary.responseJson

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

    private var progress: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = activity?.findViewById(R.id.progress)
    }

    private lateinit var job: Job

    override fun onStart() {
        super.onStart()

        job = launch(UI) {
            try {
                progress?.visibility = View.VISIBLE
                val result = responseJson("https://vassuv.ru/api/v1/register/confirm/", hashMapOf())
                textView.text = result.toString()
            } finally {
                progress?.visibility = View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()

        job.cancel()
    }
}
