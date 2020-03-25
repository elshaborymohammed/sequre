package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.compact.content.CompactPreference
import com.google.gson.Gson
import com.ocs.sequre.domain.entity.ProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfilePreference @Inject
constructor(private val sharedPreferences: SharedPreferences) :
    CompactPreference<ProfileResponse>() {

    override fun key(): String {
        return "second_opinion"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    override fun get(): ProfileResponse {
        return Gson().fromJson(
            sharedPreferences().getString(key(), ""),
            ProfileResponse::class.java
        )
    }
}