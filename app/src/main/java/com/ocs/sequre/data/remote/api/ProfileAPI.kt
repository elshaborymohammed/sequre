package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.response.ProfileResponse
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Profile
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

@Requester
interface ProfileAPI {
    @GET("me")
    fun get(): Single<ResponseSuccess<ProfileResponse>>

    @PUT("me")
    fun update(@Body body: Profile): Completable
}