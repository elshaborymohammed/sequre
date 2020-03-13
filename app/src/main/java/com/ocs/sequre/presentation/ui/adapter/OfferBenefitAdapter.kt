package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.Offer
import kotlinx.android.synthetic.main.card_offer.view.*

class OfferBenefitAdapter(private val data: List<Offer>) :
    RecyclerView.Adapter<OfferBenefitAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_offer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: (it: Offer) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: Offer) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, it: Offer) {
            itemView.apply {
                name.text = it.name
                details.text = it.details
                read_more.setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(it: Offer)
    }
}