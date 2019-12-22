package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class User(
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("countryCode")
    @Expose
    val countryCode: String,
    @SerializedName("mobile")
    @Expose
    private val _mobile: String = "",
    @SerializedName("email")
    @Expose
    val email: String = "",
    @SerializedName("password")
    @Expose
    val password: String = ""
) : Serializable {

    val mobile: String
        get() = _mobile.replaceFirst("0", "")

    override fun toString(): String {
        return "User(name='$name', countryCode='$countryCode', mobile='$mobile', email='$email', password='$password')"
    }
}