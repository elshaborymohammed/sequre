package com.ocs.sequre.data.remote.model.response.success

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("token")
    @Expose
    val token: String
)