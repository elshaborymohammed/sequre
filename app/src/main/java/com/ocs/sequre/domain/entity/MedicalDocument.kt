package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MedicalDocument(
    @SerializedName("medical")
    @Expose
    val medical: List<Document>,
    @SerializedName("lab")
    @Expose
    val lab: List<Document>,
    @SerializedName("radiology")
    @Expose
    val radiology: List<Document>
)