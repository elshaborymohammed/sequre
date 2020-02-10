package com.ocs.sequre.data.remote.model.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ocs.sequre.domain.entity.Registration

data class AuthModel(
    @SerializedName("user")
    @Expose
    val user: Registration,
    @SerializedName("token")
    @Expose
    val token: String
)