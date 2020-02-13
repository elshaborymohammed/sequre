package com.ocs.sequre.app.base

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.graphics.drawable.toBitmap
import com.compact.helper.ImageHelper

fun ProgressBar.loading(it: Boolean) {
    visibility = if (it) View.VISIBLE else View.GONE
}

fun ImageView.base64(): String? {
    return drawable?.toBase64()
}

fun Drawable.toBase64(): String {
    return "data:image/png;base64,${ImageHelper.encodeBitmapToBase64(toBitmap())}"
}