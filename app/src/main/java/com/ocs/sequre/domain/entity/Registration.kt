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
    mobile: String = "",
    @SerializedName("email")
    @Expose
    val email: String = "",
    @SerializedName("password")
    @Expose
    val password: String = ""
) : Serializable {

    @SerializedName("mobile")
    @Expose
    val mobile: String = if (mobile.startsWith("0", true)) mobile.substring(1) else mobile

    override fun toString(): String {
        return "User(name='$name', countryCode='$countryCode', mobile='$mobile', email='$email', password='$password')"
    }
}