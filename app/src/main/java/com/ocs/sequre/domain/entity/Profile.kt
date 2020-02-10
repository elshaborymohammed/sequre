package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

open class Profile(
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("email")
    @Expose
    val email: String = "",
    @SerializedName("c_code")
    @Expose
    val countryCode: String,
    phone: String = "",
    @SerializedName("relation")
    @Expose
    val relation: String = "",
    @SerializedName("birth")
    @Expose
    val birthDate: Date? = null,
    @SerializedName("gender")
    @Expose
    val gender: Int = 0,
    @SerializedName("country")
    @Expose
    val country: String? = null,
    @SerializedName("city")
    @Expose
    val city: String? = null,
    @SerializedName("area")
    @Expose
    val area: String? = null,
    @SerializedName("street")
    @Expose
    val street: String? = null,
    @SerializedName("photo")
    @Expose
    val photo: String? = null
) : Serializable {


    @SerializedName("mobile")
    @Expose
    val phone: String = if (phone.startsWith("0", true)) phone.substring(1) else phone
}