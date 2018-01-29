package ru.vassuv.startapp.screen.start

import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.android.synthetic.main.fragment_test1.*
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.routing.Router


@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {
    val list = arrayListOf(FrmFabric.TEST1.name,
            FrmFabric.TEST2.name)

    val listener: IClickListener = object : IClickListener {
        override fun onClick(id: Int, v: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Router.navigateToWithAnimate(list[id]) {
                    addSharedElement(v, ViewCompat.getTransitionName(v))
                }
            } else {
                Router.navigateTo(list[id])
            }
        }
    }

    fun getAdapter() = Adapter()

    interface IClickListener {
        fun onClick(id: Int, v: View)
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
        override fun onBindViewHolder(holder: Holder?, position: Int) {
            holder?.textView?.text = list[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_start, parent, false))
        }

        override fun getItemCount(): Int = list.size

        inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.textView)
            private val button: View = itemView.findViewById(R.id.shared_view)
            private val shared: View = itemView.findViewById(R.id.shared_button)

            init {
                button.setOnClickListener { listener.onClick(layoutPosition, shared) }
            }
        }
    }
}
