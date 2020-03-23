package com.ocs.sequre.data.remote.model.request.secondopinion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MedicalDocumentBody(
    @Expose @SerializedName("opinion_id") var opinionId: Int? = null,
    @Expose @SerializedName("category") @DocumentType var category: Int,
    @Expose @SerializedName("file") var photo: String
) {

    companion object {
        const val NEW = 0
        const val MEDICAL = 1
        const val LAB = 2
        const val RADIOLOGY = 3
    }
}