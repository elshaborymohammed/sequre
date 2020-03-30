package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.ocs.sequre.app.base.BasePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) : BasePreference<String>() {

    override fun key(): String {
        return "token"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun hasToken(): Boolean {
        return !get().isNullOrEmpty()
    }

    override fun clear(): Boolean {
        return sharedPreferences().edit().clear().commit()
    }
}