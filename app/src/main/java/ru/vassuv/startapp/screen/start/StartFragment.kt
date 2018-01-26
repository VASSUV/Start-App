package ru.vassuv.startapp.screen.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.vassuv.startapp.R
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import ru.vassuv.startapp.fabric.FrmFabric

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private lateinit var job: Job

    override fun onStart() {
        super.onStart()
        val progress = activity.findViewById<ProgressBar>(R.id.progress)

        job = launch(UI) {
            progress.visibility = View.VISIBLE

            try {
                request()
            } finally {
                progress.visibility = View.GONE
            }
        }
    }

    private suspend fun request() =  withContext(CommonPool) { Thread.sleep(5000) }

    override fun onStop() {
        super.onStop()

        if (!job.isCompleted) {
            runBlocking {
                job.cancelChildren()
                job.cancelAndJoin()
            }
        }
    }
}
