package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login(
    mobile: String,
    @SerializedName("password")
    @Expose
    val password: String
) {
    @SerializedName("mobile")
    @Expose
    val mobile: String = if (mobile.startsWith("0", true)) mobile.substring(1) else mobile


    override fun toString(): String {
        return "Login(mobile='$mobile', password='$password')"
    }
}