package com.ocs.sequre.data.remote.model.response.error

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    @Expose
    val code: Int,
    @SerializedName("errors")
    @Expose
    val errors: List<Error>
) {
    override fun toString(): String {
        return "ErrorResponse(code=$code, errors=$errors)"
    }
}