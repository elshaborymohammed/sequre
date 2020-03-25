package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.adapter.ImageTypeAdapter
import java.io.Serializable

data class Document(
    @SerializedName("id")
    @Expose
    val id: Int,
    @JsonAdapter(ImageTypeAdapter::class)
    @SerializedName("filename")
    @Expose
    val photo: String? = null
) : Serializable