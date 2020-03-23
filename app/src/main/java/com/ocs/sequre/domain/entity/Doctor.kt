package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Doctor(

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("logo")
    @Expose
    val logo: String? = null,

    @field:SerializedName("description")
    @Expose
    val description: String? = null
)