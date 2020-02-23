package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.adapter.GenderTypeAdapter
import com.ocs.sequre.data.adapter.ImageTypeAdapter
import java.text.SimpleDateFormat
import java.util.*

open class Profile constructor(
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
    @JsonAdapter(GenderTypeAdapter::class)
    @SerializedName("gender")
    @Expose
    val gender: String? = null,
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
    @JsonAdapter(ImageTypeAdapter::class)
    @SerializedName("photo")
    @Expose
    val photo: String? = null
) {

    @SerializedName("mobile")
    @Expose
    val phone: String? =
        phone?.apply { if (startsWith("0", true)) substring(1) else this }
}

