package com.ocs.sequre.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.ocs.sequre.domain.entity.UserRole

class UserRoleTypeAdapter : TypeAdapter<UserRole>() {
    override fun write(out: JsonWriter?, value: UserRole?) {
        out?.apply {
            value?.apply {
                value(type())
            }
        }
    }

    override fun read(`in`: JsonReader?): UserRole {
        `in`?.apply {
            while (hasNext()) {
                return when (nextInt()) {
                    0 -> {
                        UserRole.CORPORATE
                    }
                    1 -> {
                        UserRole.INDIVIDUAL
                    }
                    else -> {
                        UserRole.CORPORATE
                    }
                }
            }
        }
        return UserRole.INDIVIDUAL
    }
}