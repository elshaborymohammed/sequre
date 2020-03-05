package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.adapter.GenderTypeAdapter
import com.ocs.sequre.data.adapter.ImageTypeAdapter
import com.ocs.sequre.data.adapter.RelationshipTypeAdapter
import java.io.Serializable

class Dependent(
    @SerializedName("id")
    @Expose
    val id: Int,
    @JsonAdapter(RelationshipTypeAdapter::class)
    @SerializedName("relationship")
    @Expose
    val relationship: Relationship,
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
    @SerializedName("birth")
    @Expose
    val birthDate: String,
    @JsonAdapter(GenderTypeAdapter::class)
    @SerializedName("gender")
    @Expose
    val gender: String,
    @JsonAdapter(ImageTypeAdapter::class)
    @SerializedName("photo")
    @Expose
    val photo: String? = null
) : Serializable {

    @SerializedName("mobile")
    @Expose
    val phone: String = if (phone.startsWith("0", true)) phone.substring(1) else phone

    override fun toString(): String {
        return "Dependent(id=$id, relationship='$relationship', name='$name', email='$email', countryCode='$countryCode', birthDate='$birthDate', gender='$gender', photo=$photo, phone='$phone')"
    }
}