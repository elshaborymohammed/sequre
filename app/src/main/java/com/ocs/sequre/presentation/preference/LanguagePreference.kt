package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.ocs.sequre.app.base.BasePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagePreference @Inject
constructor(private val sharedPreferences: SharedPreferences) : BasePreference<String>() {

    override fun key(): String {
        return "lang"
    }

    override fun defValue(): String {
        return "en"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}