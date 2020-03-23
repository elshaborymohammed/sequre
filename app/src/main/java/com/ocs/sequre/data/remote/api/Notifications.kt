package com.ocs.sequre.data.remote.api

import com.compact.requester.annotation.Requester
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Notification
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 *Created by marco on 2020-03-18
 */
@Requester
interface Notifications {

    @Headers("Content-Type: application/json")
    @GET("https://sequre.getsandbox.com/notifications/getHistory")
    fun all(): Single<ResponseSuccess<List<Notification>>>

}