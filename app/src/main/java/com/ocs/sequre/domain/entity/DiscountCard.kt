package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DiscountCard(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose val name: String,
    @SerializedName("price")
    @Expose
    val price: Double
)