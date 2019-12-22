package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("id")
    @Expose
    private val id: Int,
    @SerializedName("name")
    @Expose
    private val name: String,
    @SerializedName("image")
    @Expose
    private val image: String
)