package com.ocs.sequre.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.ocs.sequre.BuildConfig

class ImageTypeAdapter : TypeAdapter<String>() {

    override fun read(reader: JsonReader?): String {
        reader?.apply {
            return "${BuildConfig.Image_BASE_URL}${reader.nextString()}"
        }
        return nullSafe().read(reader)
    }

    override fun write(out: JsonWriter?, value: String?) {
        out?.apply {
            value?.apply {
                value(this)
            }
        }
    }
}