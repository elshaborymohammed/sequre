package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServiceProvider(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("address")
    @Expose
    val address: String,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("photo")
    @Expose
    val photo: String
)