package com.ocs.sequre.data.remote.model.response.success

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseSuccess<T>(
    @SerializedName("result")
    @Expose
    val result: T
)