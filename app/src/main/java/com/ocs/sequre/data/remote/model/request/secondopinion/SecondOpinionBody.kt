package com.ocs.sequre.data.remote.model.request.secondopinion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecondOpinionBody(
    @Expose @SerializedName("for") @ForWho var forOther: Int,
    @Expose @SerializedName("dependent_id") var dependentId: Int,
    @Expose @SerializedName("date") var date: String,
    @Expose @SerializedName("speciality_id") var specialityId: Int,
    @Expose @SerializedName("pain_id") var painId: Int,
    @Expose @SerializedName("description") var description: String
) {
    companion object {
        const val FOR_ME = 0
        const val FOR_OTHER = 1
    }
}