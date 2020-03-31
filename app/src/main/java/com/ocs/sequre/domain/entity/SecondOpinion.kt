package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.remote.model.request.secondopinion.ForWho

sealed class SecondOpinion {
    data class Response(
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("for") val forOther: Int,
        @Expose @SerializedName("dependent_id") val dependentId: Int,
        @Expose @SerializedName("date") val date: String,
        @Expose @SerializedName("speciality_id") val specialityId: Int,
        @Expose @SerializedName("pain_id") val painId: Int,
        @Expose @SerializedName("description") val description: String
    )

    sealed class Request : SecondOpinion() {
        class AskForWho(
            val data: Body.Data? = null,
            var forMeListener: () -> Unit,
            var forOtherListener: () -> Unit
        ) : Request()

        class ChooseSpeciality(
            val specialities: List<Speciality>,
            val data: Body.Data? = null,
            var listener: (specialityId: Int, painId: Int, description: String) -> Unit
        ) : Request()

        data class YesNo(
            val question: Question,
            var listener: (question: Question, answer: Int) -> Unit
        ) : Request()

        data class MultiChoice(
            val question: Question,
            var listener: (question: Question, answer: List<Int>) -> Unit
        ) : Request()
    }

    sealed class Body {
        companion object {
            const val FOR_ME = 0
            const val FOR_OTHER = 1
        }

        data class Data(
            @Expose @SerializedName("id") var id: Int? = null,
            @Expose @SerializedName("for") @ForWho var forWho: Int? = null,
            @Expose @SerializedName("dependent_id") var dependentId: Int? = null,
            @Expose @SerializedName("date") var date: String? = null,
            @Expose @SerializedName("speciality_id") var specialityId: Int? = null,
            @Expose @SerializedName("pain_id") var painId: Int? = null,
            @Expose @SerializedName("description") var description: String? = null
        ) : Body()

        data class SpecialityAnswer(
            @Expose @SerializedName("pain_q1_id") var painQ1Id: Int? = null,
            @Expose @SerializedName("pain_q1_answer") var painQ1Answer: Int? = null,
            @Expose @SerializedName("pain_q2_id") var painQ2Id: Int? = null,
            @Expose @SerializedName("pain_q2_answer") var painQ2Answer: List<Int>? = null
        ) : Body()

        data class GeneralAnswer(
            @Expose @SerializedName("general_q1_id") var generalQ1Id: Int? = null,
            @Expose @SerializedName("general_q1_answer") var generalQ1Answer: Int? = null,
            @Expose @SerializedName("general_q2_id") var generalQ2Id: Int? = null,
            @Expose @SerializedName("general_q2_answer") var generalQ2Answer: Int? = null,
            @Expose @SerializedName("general_q3_id") var generalQ3Id: Int? = null,
            @Expose @SerializedName("general_q3_answer") var generalQ3Answer: Int? = null,
            @Expose @SerializedName("general_q4_id") var generalQ4Id: Int? = null,
            @Expose @SerializedName("general_q4_answer") var generalQ4Answer: Int? = null,
            @Expose @SerializedName("general_q5_id") var generalQ5Id: Int? = null,
            @Expose @SerializedName("general_q5_answer") var generalQ5Answer: List<Int>? = null
        ) : Body()
    }
}