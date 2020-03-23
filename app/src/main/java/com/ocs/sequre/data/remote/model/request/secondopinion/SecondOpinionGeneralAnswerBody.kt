package com.ocs.sequre.data.remote.model.request.secondopinion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecondOpinionGeneralAnswerBody(
    @Expose @SerializedName("general_q1_id") val generalQ1Id: Int? = null,
    @Expose @SerializedName("general_q1_answer") val generalQ1Answer: Int? = null,
    @Expose @SerializedName("general_q2_id") val generalQ2Id: Int? = null,
    @Expose @SerializedName("general_q2_answer") val generalQ2Answer: Int? = null,
    @Expose @SerializedName("general_q3_id") val generalQ3Id: Int? = null,
    @Expose @SerializedName("general_q3_answer") val generalQ3Answer: Int? = null,
    @Expose @SerializedName("general_q4_id") val generalQ4Id: Int? = null,
    @Expose @SerializedName("general_q4_answer") val generalQ4Answer: Int? = null,
    @Expose @SerializedName("general_q5_id") val generalQ5Id: Int? = null,
    @Expose @SerializedName("general_q5_answer") val generalQ5Answer: List<Int>? = null
)