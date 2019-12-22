package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Verification(
    @SerializedName("mobile")
    @Expose
    val mobile: String,
    @SerializedName("code")
    @Expose
    val code: Int
) {
    override fun toString(): String {
        return "Verification(mobile='$mobile', code=$code)"
    }
}