package com.ocs.sequre.data.remote.model.request.secondopinion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecondOpinionSpecialityAnswerBody(
    @Expose @SerializedName("pain_q1_id") val painQ1Id: Int,
    @Expose @SerializedName("pain_q1_answer") val painQ1Answer: Int,
    @Expose @SerializedName("pain_q2_id") val painQ2Id: Int,
    @Expose @SerializedName("pain_q2_answer") val painQ2Answer: List<Int>
)