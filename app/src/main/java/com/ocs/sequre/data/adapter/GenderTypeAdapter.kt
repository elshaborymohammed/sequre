package com.ocs.sequre.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class GenderTypeAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter?, value: String?) {
        out?.apply {
            value?.apply {
                if (this == "Male") {
                    0
                } else {
                    1
                }
            }
        }
    }

    override fun read(`in`: JsonReader?): String {
        `in`?.apply {
            while (hasNext()) {
                return when (nextInt()) {
                    0 -> {
                        "Male"
                    }
                    else -> {
                        "Female"
                    }
                }
            }
        }
        return "Male"
    }
}