package ru.vassuv.startapp.screen.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.vassuv.startapp.R
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.json.JsonObject
import ru.vassuv.startapp.utils.atlibrary.response
import ru.vassuv.startapp.utils.atlibrary.responseJson

class StartFragment : BaseFragment(), StartView {
    override val type = FrmFabric.START

    @InjectPresenter
    lateinit var presenter: StartPresenter

    companion object {
        fun newInstance(args: Bundle): StartFragment {
            val fragment = StartFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
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
                val result = responseJson("https://vassuv.ru/api/v1/register/", hashMapOf())
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
