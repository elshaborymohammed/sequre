package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.data.remote.model.request.auth.Resend
import com.ocs.sequre.data.remote.model.request.auth.Verification
import com.ocs.sequre.data.remote.model.response.success.AccessToken
import com.ocs.sequre.domain.entity.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


@Requester
interface AuthApi {

    @POST("login")
    fun login(@Body body: Login): Single<AccessToken>

    @POST("register")
    fun register(@Body body: User): Single<AccessToken>

    @POST("check/register")
    fun check(@Body body: AuthValidation): Single<Void>

    @POST("resend")
    fun resend(@Body body: Resend): Single<Void>

    @GET("countries")
    fun countries(): Single<Void>
}