package ru.vassuv.startapp.screen.isold.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_start.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.screen.BaseFragment
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import com.arellomobile.mvp.presenter.PresenterType
import java.util.TreeSet

class StartFragment : BaseFragment(), StartView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "globalStart")
    lateinit var presenter: StartPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun setSharedNameByPosition(name: String, pos: Int) {
        val adapter = recyclerView.adapter as StartPresenter.Adapter
        adapter.transitionName = name
        adapter.transitionPosition = pos
        adapter.notifyItemChanged(pos)


        val set = TreeSet<Number>()
        set.add(10L)
        set.add(10)
        set.add(10.0)

        activity?.title = set.size.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = presenter.adapter
    }

    override fun setText(text: String) {
    }

    override fun setEnterTransition(transition: Transition) {
        enterTransition = transition
    }
}
