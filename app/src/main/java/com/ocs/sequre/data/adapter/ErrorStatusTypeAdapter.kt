package com.ocs.sequre.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus

class ErrorStatusTypeAdapter : TypeAdapter<ErrorStatus>() {
    override fun write(out: JsonWriter?, value: ErrorStatus?) {
        out?.apply {
            value?.apply {
                value(type())
            }
        }
    }

    override fun read(`in`: JsonReader?): ErrorStatus {
        `in`?.apply {
            while (hasNext()) {
                return when (nextInt()) {
                    ErrorStatus.VALIDATION.type() -> {
                        ErrorStatus.VALIDATION
                    }
                    ErrorStatus.UNPROCESSED.type() -> {
                        ErrorStatus.UNPROCESSED
                    }
                    else -> {
                        ErrorStatus.ERROR
                    }
                }
            }
        }
        return ErrorStatus.ERROR
    }
}