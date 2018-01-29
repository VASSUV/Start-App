package ru.vassuv.startapp.screen.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.atlibrary.BaseFragment

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun setText(text: String) {
//        textView.text = text
    }
}
