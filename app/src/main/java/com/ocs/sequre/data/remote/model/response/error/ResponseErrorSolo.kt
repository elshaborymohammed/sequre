package com.ocs.sequre.data.remote.model.response.error

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.data.adapter.ErrorStatusTypeAdapter

data class ResponseErrorSolo(
    @JsonAdapter(ErrorStatusTypeAdapter::class)
    @SerializedName("code")
    @Expose
    val code: ErrorStatus,
    @SerializedName("errors")
    @Expose
    val errors: Error
) {
    override fun toString(): String {
        return "ErrorResponse(code=$code, errors=$errors)"
    }
}