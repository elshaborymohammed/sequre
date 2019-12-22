package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Category
import kotlinx.android.synthetic.main.card_brand_item.view.*

class CategoryAdapter :
    CompactRecyclerView.Adapter<Category, CategoryAdapter.ViewHolder>() {

    override fun layoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_category_item,
                parent,
                false
            )
        )
    }

    class ViewHolder(itemView: View) : CompactRecyclerView.ViewHolder<Category>(itemView) {
        override fun bind(position: Int, obj: Category) {
            itemView.name.text = obj.name
            GlideApp.with(itemView)
                //.load(obj.image)
                .load(R.drawable.ic_lg)
                .placeholder(R.drawable.ic_house)
                .into(itemView.image)
        }
    }
}