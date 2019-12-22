package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.ocs.sequre.app.base.BasePreference
import com.ocs.sequre.domain.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) : BasePreference<User>() {
    override fun key(): String {
        return "user"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}