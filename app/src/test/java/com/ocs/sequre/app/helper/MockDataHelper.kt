package com.ocs.sequre.app.helper

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * this helper contains all mock data paths and method which convert string into objects
 */
object MockDataHelper {
    inline fun <reified T> fromJson(path: String): T {
        val json = loadJson(path)
        return Gson().fromJson(json, T::class.java)
    }

    fun <T> loadJson(path: String, type: Type): T {
        val json = loadJson(path)
        return Gson().fromJson(json, type) as T
    }

    fun <T> loadJson(path: String, clazz: Class<T>): T {
        val json = loadJson(path)
        return Gson().fromJson(json, clazz)
    }

//    inline fun <reified T> getType(): Type {
//        return object : TypeToken<T>() {}.type
//    }

    fun loadJson(path: String): String {
        try {
            val sb = StringBuilder()
            val reader = BufferedReader(
                InputStreamReader(
                    MockDataPathHelper::class.java.classLoader!!.getResourceAsStream("mock/$path")
                )
            )

            reader.lines().forEach { sb.append(it) }
            return sb.toString()
        } catch (e: IOException) {
            throw IllegalArgumentException("Could not read from resource at: $path")
        }

    }
}
