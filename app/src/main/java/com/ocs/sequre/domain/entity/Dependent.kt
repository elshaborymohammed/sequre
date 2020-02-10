package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Dependent(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("c_code")
    @Expose
    val countryCode: String,
    phone: String = "",
    @SerializedName("relation")
    @Expose
    val relation: String,
    @SerializedName("birth")
    @Expose
    val birthDate: String,
    @SerializedName("gender")
    @Expose
    val gender: Int,
    @SerializedName("photo")
    @Expose
    val photo: String
) : Serializable {

    @SerializedName("mobile")
    @Expose
    val phone: String = if (phone.startsWith("0", true)) phone.substring(1) else phone
}