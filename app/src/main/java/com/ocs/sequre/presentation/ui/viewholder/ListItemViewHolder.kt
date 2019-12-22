package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_items.view.*

class ListItemViewHolder {

    var list: RecyclerView

    constructor(view: View) {
        list = view.list_item
    }
}