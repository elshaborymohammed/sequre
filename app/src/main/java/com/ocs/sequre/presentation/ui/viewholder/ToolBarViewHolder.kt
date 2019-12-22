package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.ocs.sequre.R

class ToolBarViewHolder {

    val toolbar: Toolbar

    constructor(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }
}