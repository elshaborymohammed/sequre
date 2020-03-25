package com.ocs.sequre.presentation.preference

import android.content.SharedPreferences
import com.compact.content.CompactPreference
import com.google.gson.Gson
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionCachedData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecondOpinionPreference @Inject
constructor(private val sharedPreferences: SharedPreferences) :
    CompactPreference<SecondOpinionCachedData>() {

    override fun key(): String {
        return "second_opinion"
    }

    override fun sharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    override fun get(): SecondOpinionCachedData {
        val get = Gson().fromJson(
            sharedPreferences().getString(key(), ""),
            SecondOpinionCachedData::class.java
        )
        return get
    }
}