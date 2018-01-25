package ru.vassuv.startapp.screen.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import ru.vassuv.startapp.utils.atlibrary.responseJson

class IntroFragment : BaseFragment(), IntroView {
    override val type = FrmFabric.INTRO

    companion object {
        fun newInstance(args: Bundle): IntroFragment {
            val fragment: IntroFragment = IntroFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: IntroPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    private lateinit var progress: ProgressBar

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = activity.findViewById(R.id.progress)
    }

    private lateinit var job: Job

    override fun onStart() {
        super.onStart()

        job = launch(UI) {
            try {
                progress.visibility = View.VISIBLE
                val result = responseJson("https://vassuv.ru/api/v1/login/", hashMapOf())
                textView.text = result.toString()
            } finally {
                progress.visibility = View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()

        job.cancel()
    }
}
