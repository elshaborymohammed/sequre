package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthValidation(
    @SerializedName("mobile")
    @Expose
    val mobile: String,
    @SerializedName("email")
    @Expose
    val email: String
) {
    override fun toString(): String {
        return "AuthValidation(mobile='$mobile', email='$email')"
    }
}