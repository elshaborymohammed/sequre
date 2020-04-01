package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login(
    phone: String,
    @SerializedName("password")
    @Expose
    val password: String,
    @SerializedName("device_token")
    @Expose
    val device_token: String = "lhkj",
    @SerializedName("os_name")
    @Expose
    val os_name: String = "Android"
) {
    @SerializedName("mobile")
    @Expose
    val phone: String = if (phone.startsWith("0", true)) phone.substring(1) else phone


    override fun toString(): String {
        return "Login(phone='$phone', password='$password')"
    }
}