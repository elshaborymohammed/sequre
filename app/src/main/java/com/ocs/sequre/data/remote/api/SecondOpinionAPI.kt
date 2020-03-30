package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

@Requester
interface SecondOpinionAPI {

    @POST("opinion")
    fun post(@Body body: SecondOpinion.Body.Data): Single<ResponseSuccess<SecondOpinion.Response>>

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.Data): Completable

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.SpecialityAnswer): Completable

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.GeneralAnswer): Completable

    @GET("opinion/getSpecialities")
    fun specialities(): Single<ResponseSuccess<List<Speciality>>>

    @GET("opinion/getPainQuestions/{painId}")
    fun painQuestions(@Path("painId") painId: Int): Single<ResponseSuccess<List<Question>>>

    @GET("opinion/getGeneralQuestions/{painId}")
    fun generalQuestions(@Path("painId") painId: Int): Single<ResponseSuccess<List<Question>>>

    @GET("serviceProvider/getAll/{id}")
    fun doctors(@Path("id") id: Int): Single<ResponseSuccess<List<Doctor>>>

    @GET("https://sequre.getsandbox.com/opinion/getReport/{id}")
    fun getReport(@Path("id") id: Int): Single<ResponseSuccess<Report>>
}