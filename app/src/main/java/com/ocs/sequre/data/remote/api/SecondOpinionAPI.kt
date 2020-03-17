package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Question
import com.ocs.sequre.domain.entity.Speciality
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@Requester
interface SecondOpinionAPI {

    @GET("opinion/getSpecialities")
    fun specialities(): Single<ResponseSuccess<List<Speciality>>>

    @GET("opinion/getPainQuestions/{id}")
    fun painQuestions(@Path("id") id: Int): Single<ResponseSuccess<List<Question>>>

    @GET("opinion/getGeneralQuestions/{id}")
    fun generalQuestions(@Path("id") id: Int): Single<ResponseSuccess<List<Question>>>
}