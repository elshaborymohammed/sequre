package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.DiscountCard
import kotlinx.android.synthetic.main.card_discount_card.view.*

class DiscountCardAdapter :
    CompactRecyclerView.Adapter<DiscountCard, DiscountCardAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun layoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            CompactRecyclerView.SpacesItemDecoration.Grid.builder(context).space(0).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_discount_card, parent, false)
        )
    }

    fun setOnItemClickListener(listener: (it: DiscountCard) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: DiscountCard) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) :
        CompactRecyclerView.ViewHolder<DiscountCard>(itemView) {
        override fun bind(position: Int, it: DiscountCard) {
            itemView.apply {
                setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }
                name.text = it.name
                price.text = it.price.toString()
            }
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(it: DiscountCard)
    }
}