package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionGeneralAnswerBody
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionSpecialityAnswerBody
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

@Requester
interface SecondOpinionAPI {

    @POST("opinion")
    fun post(@Body body: SecondOpinionBody): Single<ResponseSuccess<SecondOpinion>>

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinionSpecialityAnswerBody): Completable

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinionGeneralAnswerBody): Completable

    @GET("opinion/getSpecialities")
    fun specialities(): Single<ResponseSuccess<List<Speciality>>>

    @GET("opinion/getPainQuestions/{id}")
    fun painQuestions(@Path("id") id: Int): Single<ResponseSuccess<List<Question>>>

    @GET("opinion/getGeneralQuestions/{id}")
    fun generalQuestions(@Path("id") id: Int): Single<ResponseSuccess<List<Question>>>

    @Headers("Content-Type: application/json")
    @GET("https://sequre.getsandbox.com/serviceProvider/getAll/{id}")
    fun doctors(@Path("id") id: Int): Single<ResponseSuccess<List<Doctor>>>

    @GET("https://sequre.getsandbox.com/opinion/getReport/{id}")
    fun getReport(@Path("id") id: Int): Single<ResponseSuccess<Report>>
}