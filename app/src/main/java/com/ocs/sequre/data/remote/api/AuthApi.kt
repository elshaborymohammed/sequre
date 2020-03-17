package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.domain.entity.AuthModel
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Country
import com.ocs.sequre.domain.entity.Registration
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@Requester
interface AuthAPI {

    @POST("auth/login")
    fun login(@Body body: Login): Single<ResponseSuccess<AuthModel>>

    @POST("auth/register")
    fun register(@Body body: Registration): Single<ResponseSuccess<AuthModel>>

    @POST("auth/check/register")
    fun check(@Body body: AuthValidation): Completable

    @GET("location/countries/codes")
    fun countryCode(): Single<ResponseSuccess<List<Country>>>
}