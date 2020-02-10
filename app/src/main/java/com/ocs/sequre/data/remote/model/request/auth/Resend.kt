package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Resend(
    @SerializedName("mobile")
    @Expose
    val phone: String,
    @SerializedName("password")
    @Expose
    val password: String
) {
    override fun toString(): String {
        return "Verification(phone='$phone', password=$password)"
    }
}