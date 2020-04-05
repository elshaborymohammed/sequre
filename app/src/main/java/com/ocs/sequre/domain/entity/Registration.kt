package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Registration(
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("c_code")
    @Expose
    val countryCode: String,
    phone: String = "",
    @SerializedName("email")
    @Expose
    val email: String = "",
    @SerializedName("password")
    @Expose
    val password: String = "",
    @SerializedName("device_token")
    @Expose
    var device_token: String = "",
    @SerializedName("os_name")
    @Expose
    val os_name: String = "Android"
) : Serializable {

    @SerializedName("mobile")
    @Expose
    val phone: String = if (phone.startsWith("0", true)) phone.substring(1) else phone

    override fun toString(): String {
        return "User(name='$name', countryCode='$countryCode', phone='$phone', email='$email', password='$password')"
    }
}