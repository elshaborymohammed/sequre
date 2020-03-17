package com.ocs.sequre.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("order")
    @Expose
    val order: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("fields")
    @Expose
    val fields: List<Filed>
) {
    data class Filed(
        @SerializedName("key")
        @Expose
        val key: String,
        @SerializedName("val")
        @Expose
        val value: String
    ) {
        override fun toString(): String {
            return "Filed(key='$key', value='$value')"
        }
    }

    override fun toString(): String {
        return "Question(id=$id, order=$order, name='$name', fields=$fields)"
    }
}