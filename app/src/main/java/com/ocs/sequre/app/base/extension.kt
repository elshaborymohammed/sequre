package com.ocs.sequre.app.base

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.loading(it: Boolean) {
    visibility = if (it) View.VISIBLE else View.GONE
}