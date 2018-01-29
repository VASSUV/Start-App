package ru.vassuv.startapp.screen.start

import android.os.Bundle
import android.support.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_start.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition

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
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = presenter.getAdapter()
    }

    override fun setText(text: String) {
    }

    override fun setEnterTransition(transition: Transition) {
        enterTransition = transition
    }
}
