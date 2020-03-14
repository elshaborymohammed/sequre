package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.Offer
import kotlinx.android.synthetic.main.card_offer_benefit.view.*

class OfferBenefitAdapter(private val data: List<Offer>) :
    RecyclerView.Adapter<OfferBenefitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_offer_benefit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, it: Offer) {
            itemView.apply {
                name.text = it.name
                details.text = it.details
            }
        }
    }
}