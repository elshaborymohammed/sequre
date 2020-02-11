package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.domain.entity.Dependent
import io.reactivex.Completable
import retrofit2.http.*

@Requester
interface DependentAPI {
    @POST("me/dependents")
    fun create(@Body body: Dependent): Completable

    @PUT("me/dependents/{id}")
    fun update(@Path("id") int: Int, @Body body: Dependent): Completable

    @DELETE("me/dependents/{id}")
    fun delete(@Path("id") int: Int): Completable
}