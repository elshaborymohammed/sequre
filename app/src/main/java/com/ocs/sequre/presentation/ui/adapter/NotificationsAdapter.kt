package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.CompactRecyclerView
import com.compact.widget.recyclerview.SpaceDecoration
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.Notification
import kotlinx.android.synthetic.main.card_notification.view.*
import javax.inject.Inject

/**
 *Created by marco on 2020-03-18
 */
class NotificationsAdapter
@Inject
constructor() : CompactRecyclerView.Adapter<Notification, NotificationsAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            SpaceDecoration.builder(context).space(8).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: (it: Notification) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: Notification) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, it: Notification) {
            itemView.apply {
                name.text = it.name
                date.text = it.date
                item.setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(it: Notification)
    }
}