package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.card_dependent.view.*

class DependentAdapter :
    CompactRecyclerView.Adapter<Dependent, DependentAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            CompactRecyclerView.SpacesItemDecoration.Linear.builder(context).space(8).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_dependent, parent, false)
        )
    }

    fun setOnItemClickListener(listener: (it: Dependent) -> Unit) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnItemClickListener(it: Dependent) {
                    listener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : CompactRecyclerView.ViewHolder<Dependent>(itemView) {
        override fun bind(position: Int, it: Dependent) {
            itemView.apply {
                setOnClickListener { _ ->
                    listener.setOnItemClickListener(it)
                }

                relation.text = it.relationship
                name.text = it.name
                email.text = it.email
                phone.text = it.phone
                birth_date.text = it.birthDate
                gender.text = it.gender
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
        fun setOnItemClickListener(it: Dependent)
    }
}