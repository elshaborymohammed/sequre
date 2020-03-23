package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Doctor
import kotlinx.android.synthetic.main.card_doctor.view.*
import javax.inject.Inject

/**
 *Created by marco on 2020-03-21
 */
class DoctorsAdapter
@Inject
constructor(private val requestOptions: RequestOptions) :
    CompactRecyclerView.Adapter<Doctor, DoctorsAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_doctor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: (it: Doctor) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: Doctor) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, it: Doctor) {
            itemView.apply {
                name.text = it.name
                address.text = it.description
                GlideApp.with(this)
                    .load(it.logo)
                    .apply(requestOptions)
                    .into(image)


                item.setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(it: Doctor)
    }
}