package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorDetails(

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("logo")
    @Expose
    val logo: String? = null,

    @field:SerializedName("brief")
    @Expose
    val brief: String? = null,
    @field:SerializedName("website")
    @Expose
    val website: String? = null,
    @field:SerializedName("hotline")
    @Expose
    val hotLine: String? = null
) : Serializable