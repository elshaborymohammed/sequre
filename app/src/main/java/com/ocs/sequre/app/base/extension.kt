package com.ocs.sequre.app.base

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.annotation.ArrayRes
import androidx.core.graphics.drawable.toBitmap
import com.compact.helper.ImageHelper
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R

fun ProgressBar.loading(it: Boolean) {
    visibility = if (it) View.VISIBLE else View.GONE
}

fun ImageView.base64(): String? {
    return drawable?.toBase64()
}

fun Drawable.toBase64(): String {
    return "data:image/png;base64,${ImageHelper.encodeBitmapToBase64(toBitmap())}"
}

fun <T> TextInputLayout.setAdapter(adapter: T) where T : ListAdapter, T : Filterable {
    if (editText is AutoCompleteTextView) {
        (editText as AutoCompleteTextView).let {
            it.threshold = 1 //will start working from first character
            it.setAdapter(adapter)
        }
    }
}

fun <T> TextInputLayout.setAdapter(objects: ArrayList<T>) {
    setAdapter(ArrayAdapter(context, R.layout.select_dialog_item, R.id.text, objects))
}

fun <T> TextInputLayout.setAdapter(objects: Array<T>) {
    setAdapter(ArrayAdapter(context, R.layout.select_dialog_item, R.id.text, objects))
}

fun TextInputLayout.setAdapter(@ArrayRes arrayRes: Int) {
    setAdapter(
        ArrayAdapter(
            context,
            R.layout.select_dialog_item,
            R.id.text,
            resources.getStringArray(arrayRes)
        )
    )
}