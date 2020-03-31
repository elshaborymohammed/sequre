package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.ocs.sequre.app.base.BasePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenPreference
@Inject
constructor(private val sharedPreferences: SharedPreferences) : BasePreference<String>() {

    override fun key() = "fcm_token"


    override fun defValue(): String {
        return ""
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}