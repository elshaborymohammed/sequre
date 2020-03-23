package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecondOpinionPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) {

    private fun key(): String {
        return "second_opinion_id"
    }

    private fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun hasSecondOpinion(): Boolean {
        return -1 == get()
    }

    fun set(t: Int): Boolean {
        return sharedPreferences().edit().putInt(key(), t).commit()
    }

    fun get(): Int {
        return sharedPreferences().getInt(key(), -1)
    }

    fun clear(): Boolean {
        return sharedPreferences().edit().remove(key()).commit()
    }
}