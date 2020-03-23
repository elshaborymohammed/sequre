package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.remote.model.request.secondopinion.DocumentType
import java.io.Serializable

data class Document(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("category")
    @Expose
    @DocumentType
    val category: Int,
//    @JsonAdapter(ImageTypeAdapter::class)
    @SerializedName("photo")
    @Expose
    val photo: String? = null
) : Serializable