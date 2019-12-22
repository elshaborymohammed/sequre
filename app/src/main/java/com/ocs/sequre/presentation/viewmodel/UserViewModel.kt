package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.ocs.sequre.domain.entity.User
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.preference.LanguagePreference
import com.ocs.sequre.presentation.preference.UserPreference
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val auth: AuthPreference,
    private val language: LanguagePreference,
    private val user: UserPreference
) : CompactViewModel() {

    fun hasToken(): Boolean {
        return auth.hasToken()
    }

    fun getToken(): String? {
        return if (auth.hasToken()) {
            auth.get()
        } else {
            null
        }
    }

    fun setLang(lang: String): Boolean {
        return language.set(lang)
    }

    fun getLang(): String {
        return language.get()
    }

    fun setUser(data: User): Boolean {
        return user.set(data)
    }

    fun getUser(): User {
        return user.get()
    }
}