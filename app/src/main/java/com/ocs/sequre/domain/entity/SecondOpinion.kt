package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecondOpinion(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("for") val forOther: Int,
    @Expose @SerializedName("dependent_id") val dependentId: Int,
    @Expose @SerializedName("date") val date: String,
    @Expose @SerializedName("speciality_id") val specialityId: Int,
    @Expose @SerializedName("pain_id") val painId: Int,
    @Expose @SerializedName("description") val description: String
) 