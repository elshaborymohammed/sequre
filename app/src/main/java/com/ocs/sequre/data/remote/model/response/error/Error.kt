package com.ocs.sequre.data.remote.model.response.error

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("path")
    @Expose
    val path: String,
    @SerializedName("message")
    @Expose
    val message: String
) {
    override fun toString(): String {
        return "Error(path='$path', message='$message')"
    }
}