package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.widget.recyclerview.CompactRecyclerView
import com.compact.widget.recyclerview.SpaceDecoration
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.ServiceProvider
import kotlinx.android.synthetic.main.card_doctor.view.*

class ServiceProviderAdapter :
    CompactRecyclerView.Adapter<ServiceProvider, ServiceProviderAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            SpaceDecoration.builder(context).space(8).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_service_provider, parent, false)
        )
    }

    fun setOnItemClickListener(listener: (it: ServiceProvider) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: ServiceProvider) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) :
        CompactRecyclerView.ViewHolder<ServiceProvider>(itemView) {
        override fun bind(position: Int, it: ServiceProvider) {
            itemView.apply {
                setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }

                name.text = it.name
                address.text = it.address
                GlideApp.with(this)
                    .load(it.photo)
                    .signature(ObjectKey(it.photo ?: ""))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(image)
            }
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(it: ServiceProvider)
    }
}