package ru.vassuv.startapp.screen.start

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.vassuv.startapp.R
import ru.vassuv.startapp.fabric.FrmFabric
import ru.vassuv.startapp.utils.Router

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {
    val list = arrayListOf(FrmFabric.INTRO.name,
            FrmFabric.SPLASH.name)

    val listener: IClickListener = object : IClickListener{
        override fun onClick(id: Int) {
            Router.navigateTo(list[id])
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getAdapter() = Adapter()

    interface IClickListener {
        fun onClick(id: Int)
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

            init {
                textView.setOnClickListener { listener.onClick(layoutPosition) }
            }
        }
    }
}
