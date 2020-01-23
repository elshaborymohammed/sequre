package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.MedicalDocument
import kotlinx.android.synthetic.main.card_image.view.*

class DocumentAdapter :
    CompactRecyclerView.Adapter<MedicalDocument, DocumentAdapter.ViewHolder>() {

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            CompactRecyclerView.SpacesItemDecoration.Linear.builder(context)
                .orientation(RecyclerView.HORIZONTAL).horizontal(12).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_image, parent, false)
        )
    }

    class ViewHolder(itemView: View) : CompactRecyclerView.ViewHolder<MedicalDocument>(itemView) {
        override fun bind(position: Int, obj: MedicalDocument) {
            GlideApp.with(itemView)
                .load(R.drawable.ic_cross)
                .optionalFitCenter()
                .into(itemView.image)
        }
    }
}