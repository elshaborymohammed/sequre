package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.request.secondopinion.DocumentType
import com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.MedicalDocument
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

@Requester
interface MedicalDocumentAPI {

    @POST("opinion/files")
    fun post(@Body body: MedicalDocumentBody): Completable

    @DELETE("opinion/files/destroy/{id}/{category}")
    fun delete(@Path("id") id: Int, @Path("category") @DocumentType category: Int): Completable

    @GET("opinion/files/get/{id}")
    fun get(@Path("id") id: Int): Single<ResponseSuccess<MedicalDocument>>
}