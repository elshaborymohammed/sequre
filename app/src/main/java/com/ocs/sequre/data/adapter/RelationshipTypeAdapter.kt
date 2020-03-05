package com.ocs.sequre.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.ocs.sequre.domain.entity.Relationship

class RelationshipTypeAdapter : TypeAdapter<Relationship>() {
    override fun write(out: JsonWriter?, value: Relationship?) {
        out?.apply {
            value?.apply { value("${value.flag}") }
        }
    }

    override fun read(`in`: JsonReader?): Relationship {
        `in`?.apply {
            while (hasNext()) {
                return Relationship.valueOf(nextInt())
            }
        }
        return Relationship.OTHER
    }
}