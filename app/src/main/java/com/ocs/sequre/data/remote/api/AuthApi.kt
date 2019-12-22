package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.data.remote.model.request.auth.Resend
import com.ocs.sequre.data.remote.model.request.auth.Verification
import com.ocs.sequre.data.remote.model.response.success.AccessToken
import com.ocs.sequre.domain.entity.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

@Requester
interface AuthApi {

    @POST("auth/login")
    fun login(@Body body: Login): Single<AccessToken>

    @POST("auth/register")
    fun register(@Body body: User): Single<AccessToken>

    @POST("auth/resend")
    fun resend(@Body body: Resend): Single<Void>

    @POST("auth/verification")
    fun verification(@Body body: Verification): Single<Void>

    @POST("auth/verification")
    fun verification(): Single<Void>
}