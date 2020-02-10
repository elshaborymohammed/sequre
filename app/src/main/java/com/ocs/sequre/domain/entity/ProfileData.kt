package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ProfileData(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("c_code")
    @Expose
    val countryCode: String? = null,
    phone: String? = null,
    @SerializedName("relation")
    @Expose
    val relation: String? = null,
    @SerializedName("birth")
    @Expose
    val birthDate: String? = null,
    @SerializedName("gender")
    @Expose
    val gender: Int? = null,
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
    val photo: String? = null,
    @SerializedName("dependents")
    @Expose
    val dependents: List<Dependent>? = null
) {

    @SerializedName("mobile")
    @Expose
    val phone: String? =
        phone?.apply { if (startsWith("0", true)) substring(1) else this }
}