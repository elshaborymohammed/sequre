package com.ocs.sequre.data.remote.model.request.secondopinion

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecondOpinionCachedData(
    @Expose @SerializedName("id") var id: Int,
    @Expose @SerializedName("body") var body: SecondOpinionBody
){
    override fun toString(): String {
        return "SecondOpinionCachedData(id=$id, body=$body)"
    }
}