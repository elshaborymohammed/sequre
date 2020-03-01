package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.ocs.sequre.app.base.BasePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageStatusPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) : BasePreference<Boolean>() {

    override fun key(): String {
        return "has_language"
    }

    fun hasLang(): Boolean {
        return get()
    }

    override fun defValue(): String {
        return "false"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}