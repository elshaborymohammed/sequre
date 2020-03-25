package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.CompactRecyclerView
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.domain.entity.Document
import kotlinx.android.synthetic.main.card_medical_document.view.*

class MedicalDocumentAdapter :
    CompactRecyclerView.Adapter<Document, MedicalDocumentAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    init {
        add(Document(-1, ""))
    }

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(
            CompactRecyclerView.SpacesItemDecoration.Linear.builder(context)
                .orientation(RecyclerView.HORIZONTAL)
                .horizontal(8).build()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_medical_document, parent, false)
        )
    }

    override fun swap(data: List<Document>?) {
        val dataSet = ArrayList<Document>()
        dataSet.add(Document(-1, ""))
        data?.let { dataSet.addAll(it) }
        super.swap(dataSet)
    }

    fun setOnItemClickListener(
        onNewListener: () -> Unit,
        onChangeListener: (it: Document) -> Unit
    ) {
        setOnItemClickListener(
            object : OnItemClickListener {
                override fun setOnNewItemClickListener() {
                    onNewListener()
                }

                override fun setOnChangeItemClickListener(it: Document) {
                    onChangeListener(it)
                }
            }
        )
    }

    private fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) :
        CompactRecyclerView.ViewHolder<Document>(itemView) {
        override fun bind(position: Int, obj: Document) {
            itemView.apply {
                setOnClickListener {
                    if (obj.id == -1) {
                        listener.setOnNewItemClickListener()
                    } else {
                        listener.setOnChangeItemClickListener(obj)
                    }
                }

                if (obj.id == -1) {
                    GlideApp.with(this)
                        .load(R.drawable.ll_cross)
                        .optionalFitCenter()
                        .into(image)
                } else {
                    GlideApp.with(this)
                        .load(obj.photo)
                        .optionalFitCenter()
                        .into(image)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun setOnNewItemClickListener()
        fun setOnChangeItemClickListener(it: Document)
    }
}