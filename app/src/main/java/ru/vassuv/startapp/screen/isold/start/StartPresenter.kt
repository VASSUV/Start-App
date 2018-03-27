package ru.vassuv.startapp.screen.isold.start

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.jetbrains.anko.bundleOf
import ru.vassuv.processor.FrmFabric
import ru.vassuv.router.Router
import ru.vassuv.startapp.R

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {
    val adapter = Adapter()
    val list = arrayListOf(FrmFabric.TEST_1, FrmFabric.TEST_2, FrmFabric.TEST_3, FrmFabric.SPLASH, FrmFabric.INTRO)

    private var transitionNameCounter = 0

    val listener: IClickListener = object : IClickListener {
        override fun onClick(position: Int, sharedView: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                adapter.transitionPosition = position
                adapter.transitionName = sharedView.context.getString(R.string.shared_transition_button) + transitionNameCounter++
                sharedView.transitionName =  adapter.transitionName

                Router.navigateToWithAnimate(list[position], bundleOf("SHARED_NAME" to adapter.transitionName)) {
                    addSharedElement(sharedView, adapter.transitionName)
                }
            } else {
                Router.navigateTo(list[position])
            }
        }
    }

    interface IClickListener {
        fun onClick(position: Int, sharedView: View)
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.Holder>() {

        var transitionPosition: Int = -1
        var transitionName: String = ""

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textView.text = list[position]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transitionPosition == position) {
                holder.shared.transitionName = transitionName
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_start, parent, false))
        }

        override fun getItemCount(): Int = list.size

        inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.textView)
            private val button: View = itemView.findViewById(R.id.shared_view)
            val shared: View = itemView.findViewById(R.id.shared_button)

            init {
                button.setOnClickListener { listener.onClick(layoutPosition, if(layoutPosition == 0) button else shared) }
            }
        }

    }
}
