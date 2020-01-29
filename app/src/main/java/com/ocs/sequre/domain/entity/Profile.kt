package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Profile(
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("email")
    @Expose
    val email: String = "",
    @SerializedName("c_code")
    @Expose
    val countryCode: String,
    mobile: String = "",
    @SerializedName("relation")
    @Expose
    val relation: String = "",
    @SerializedName("date_birth")
    @Expose
    val birthDate: Date
) : Serializable {

    @SerializedName("mobile")
    @Expose
    val mobile: String = if (mobile.startsWith("0", true)) mobile.substring(1) else mobile

    override fun toString(): String {
        return "Profile(name='$name', email='$email', countryCode='$countryCode', mobile='$mobile, relation='$relation', birthData=$birthDate')"
    }
}