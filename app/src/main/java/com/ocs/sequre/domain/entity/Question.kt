package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Question(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("order") val order: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("fields") val fields: List<Filed>,
    var answer: List<String>? = null
) 