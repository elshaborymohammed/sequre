package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import kotlinx.android.synthetic.main.card_menu_item.view.*

class MenuAdapter :
    CompactRecyclerView.Adapter<MenuAdapter.Menu, MenuAdapter.ViewHolder>() {

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            CompactRecyclerView.SpacesItemDecoration.Linear.builder(context)
                .top(32).left(12).right(12).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_menu_item,
                parent,
                false
            )
        )
    }

    class ViewHolder(itemView: View) : CompactRecyclerView.ViewHolder<Menu>(itemView) {
        override fun bind(position: Int, obj: Menu) {
            GlideApp.with(itemView)
                .load(obj.res)
                .fitCenter()
                .into(itemView.image)
        }
    }

    data class Menu(@DrawableRes val res: Int, @IdRes val nav: Int) {

        companion object {
            fun getMenus(): List<Menu> {
                val data = ArrayList<Menu>()
                data.add(Menu(res = R.drawable.ic_home_white, nav = R.id.home))
                data.add(Menu(res = R.drawable.ic_about_us, nav = R.id.home))
                data.add(Menu(res = R.drawable.ic_user, nav = R.id.home))
                data.add(Menu(res = R.drawable.ic_gears, nav = R.id.home))
                data.add(Menu(res = R.drawable.ic_logout, nav = R.id.home))
                return data
            }
        }
    }
}