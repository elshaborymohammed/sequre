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
    fun post(@Body body: SecondOpinion.Body.Data): Single<ResponseSuccess<SecondOpinion.Body.Data>>

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.Data): Completable

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.SpecialityAnswer): Completable

    @PUT("opinion/{id}")
    fun put(@Path("id") id: Int, @Body body: SecondOpinion.Body.GeneralAnswer): Completable

    @GET("opinion/getLastUnFinishedRequest")
    fun get(): Single<ResponseSuccess<SecondOpinion.Body.Data>>

    @DELETE("opinion/{id}")
    fun delete(@Path("id") id: Int): Completable

    @GET("opinion/getSpecialities")
    fun specialities(): Single<ResponseSuccess<List<Speciality>>>

    @GET("opinion/getPainQuestions/{painId}")
    fun painQuestions(@Path("painId") painId: Int): Single<ResponseSuccess<List<Question>>>

    @GET("opinion/getGeneralQuestions/{painId}")
    fun generalQuestions(@Path("painId") painId: Int): Single<ResponseSuccess<List<Question>>>

    @FormUrlEncoded
    @POST("opinion/provider/choose")
    fun chooseDoctor(@Field("id") id: Int, @Field("provider_id") providerId: Int): Completable

    @GET("serviceProvider/getAll/{id}")
    fun doctors(@Path("id") id: Int): Single<ResponseSuccess<List<Doctor>>>

    @GET("opinion/getReport/{id}")
    fun getReport(@Path("id") id: Int): Single<ResponseSuccess<Report>>

    @GET("serviceProvider/getDetails/{id}")
    fun doctorDetails(@Path("id") id: Int): Single<ResponseSuccess<DoctorDetails>>
}