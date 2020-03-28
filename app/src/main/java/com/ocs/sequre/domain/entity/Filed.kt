package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Filed(
    @Expose @SerializedName("key") val key: String,
    @Expose @SerializedName("val") val value: String
) {
    override fun toString(): String {
        return "Filed(key='$key', value='$value')"
    }
}