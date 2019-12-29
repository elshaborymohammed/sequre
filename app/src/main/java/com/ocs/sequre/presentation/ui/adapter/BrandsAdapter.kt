package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Brand
import kotlinx.android.synthetic.main.card_brand_item.view.*

class BrandsAdapter :
    CompactRecyclerView.Adapter<Brand, BrandsAdapter.ViewHolder>() {

    override fun layoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_brand_item,
                parent,
                false
            )
        )
    }

    class ViewHolder(itemView: View) : CompactRecyclerView.ViewHolder<Brand>(itemView) {
        override fun bind(position: Int, obj: Brand) {
            itemView.name.text = obj.name
            GlideApp.with(itemView)
                //.load(obj.image)
                .load(R.drawable.ic_logo)
                .placeholder(R.drawable.ic_logo)
                .into(itemView.image)
        }
    }
}