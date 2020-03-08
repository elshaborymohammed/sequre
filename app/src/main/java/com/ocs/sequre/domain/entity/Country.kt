package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Country(
    @SerializedName("code")
    @Expose
    val code: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("shortName")
    @Expose
    val shortName: String
) : Serializable {
    override fun toString(): String {
        return "$code - $name"
    }
}