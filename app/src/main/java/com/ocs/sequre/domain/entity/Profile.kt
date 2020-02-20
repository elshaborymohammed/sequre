package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.adapter.GenderTypeAdapter
import com.ocs.sequre.data.adapter.ImageTypeAdapter
import java.text.SimpleDateFormat
import java.util.*

open class Profile(
    @SerializedName("name")
    @Expose
    open val name: String? = null,
    @SerializedName("email")
    @Expose
    open val email: String? = null,
    @SerializedName("c_code")
    @Expose
    open val countryCode: String? = null,
    phone: String? = null,
    @SerializedName("relation")
    @Expose
    open val relation: String? = null,
    birthDate: String? = null,
    @JsonAdapter(GenderTypeAdapter::class)
    @SerializedName("gender")
    @Expose
    open val gender: String? = null,
    @SerializedName("country")
    @Expose
    open val country: String? = null,
    @SerializedName("city")
    @Expose
    open val city: String? = null,
    @SerializedName("area")
    @Expose
    open val area: String? = null,
    @SerializedName("street")
    @Expose
    open val street: String? = null,
    @JsonAdapter(ImageTypeAdapter::class)
    @SerializedName("photo")
    @Expose
    open val photo: String? = null
) {

    @SerializedName("mobile")
    @Expose
    open val phone: String? =
        phone?.apply { if (startsWith("0", true)) substring(1) else this }

    @SerializedName("birth")
    @Expose
    open val birthDate: String? =
        birthDate.apply {
            val instance = Calendar.getInstance()
            instance.set(2002, 2, 22)

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy")
            dateFormat.format(Date())
        }

}