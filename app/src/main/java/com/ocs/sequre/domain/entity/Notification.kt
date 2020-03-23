package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *Created by marco on 2020-03-18
 */
data class Notification(
    @field:SerializedName("date")
    @Expose
    val date: String? = null,

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("source_type")
    @Expose
    val sourceType: String? = null,

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("source_id")
    @Expose
    val sourceId: Int? = null
) : Serializable {
    companion object {
        const val TYPE_OPINION = "SecondOpinion"
        const val TYPE_OFFER = "Offer"
    }
}