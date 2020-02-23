package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.app.extensions.isNotNullOrEmpty
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.card_dependent.view.*
import kotlinx.android.synthetic.main.card_dependent.view.birth_date
import kotlinx.android.synthetic.main.card_dependent.view.email
import kotlinx.android.synthetic.main.card_dependent.view.name
import kotlinx.android.synthetic.main.card_dependent.view.phone
import kotlinx.android.synthetic.main.card_profile.view.*
import java.text.SimpleDateFormat

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
                gender.text = it.gender
                birth_date?.apply {
                    text = if (it.birthDate.isNotNullOrEmpty()) {
                        val date = SimpleDateFormat("yyyy-MM-dd").parse(it.birthDate.toString())
                        SimpleDateFormat("dd-MM-yyyy").format(date)
                    } else {
                        "dd-MM-yyyy"
                    }
                }
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