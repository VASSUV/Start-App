package ru.vassuv.startapp.screen.test3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vassuv.startapp.R

import com.arellomobile.mvp.presenter.InjectPresenter
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

class Test3Fragment : BaseFragment(), Test3View {
    override val type: FrmFabric
        get() = FrmFabric.TEST3

    companion object {

        fun newInstance(args: Bundle): Test3Fragment {
            val fragment: Test3Fragment = Test3Fragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: Test3Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
