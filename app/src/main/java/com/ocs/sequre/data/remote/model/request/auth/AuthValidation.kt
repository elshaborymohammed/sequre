package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthValidation(
    @SerializedName("mobile")
    @Expose
    val phone: String? = null,
    @SerializedName("email")
    @Expose
    val email: String? = null
) {
    override fun toString(): String {
        return "AuthValidation(phone='$phone', email='$email')"
    }
}