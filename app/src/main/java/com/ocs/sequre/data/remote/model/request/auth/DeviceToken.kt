package com.ocs.sequre.data.remote.model.request.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DeviceToken(
    @SerializedName("device_token")
    @Expose
    val deviceToken: String,
    @SerializedName("os_name")
    @Expose
    val osName: String = "Android"
)