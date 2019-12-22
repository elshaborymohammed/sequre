package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("mobile")
    @Expose
    val mobile: String,
    @SerializedName("password")
    @Expose
    val password: String
) {
    override fun toString(): String {
        return "Login(mobile='$mobile', password='$password')"
    }
}