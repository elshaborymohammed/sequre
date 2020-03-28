package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.compact.content.CompactPreference
import com.google.gson.Gson
import com.ocs.sequre.domain.entity.SecondOpinion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecondOpinionPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) :
    CompactPreference<SecondOpinion.Body.Data>() {

    override fun key(): String {
        return "second_opinion"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    override fun get(): SecondOpinion.Body.Data {
        return Gson().fromJson(
            sharedPreferences().getString(key(), ""),
            SecondOpinion.Body.Data::class.java
        )
    }
}