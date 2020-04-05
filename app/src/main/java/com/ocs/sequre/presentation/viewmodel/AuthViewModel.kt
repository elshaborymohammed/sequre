package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.google.firebase.iid.FirebaseInstanceId
import com.ocs.sequre.data.remote.api.RequesterAuthAPI
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.domain.entity.AuthModel
import com.ocs.sequre.domain.entity.Country
import com.ocs.sequre.domain.entity.Registration
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.preference.FcmTokenPreference
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val api: RequesterAuthAPI,
    private val preference: AuthPreference,
    private val compose: RxCompactSchedulers,
    private val tokenPref: FcmTokenPreference
) : CompactViewModel() {

    fun login(phone: String, password: String): Single<AuthModel> {
        return api.login(
            Login(
                phone = phone,
                password = password,
                device_token = token()
            )
        )
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess { preference.set(it.token) }
    }

    fun register(body: Registration): Single<AuthModel> {
        body.device_token = token()
        return api.register(body)
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess { preference.set(it.token) }
    }

    fun countryCode(): Single<List<Country>> {
        return api.countryCode()
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun check(body: AuthValidation): Completable {
        return api.check(body)
            .compose(compose.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun checkEmail(email: String): Completable {
        return api.check(AuthValidation(email = email))
            .compose(compose.applyOnCompletable())
    }

    fun checkPhone(phone: String): Completable {
        return api.check(AuthValidation(phone = phone))
            .compose(compose.applyOnCompletable())
    }

    private fun token(): String {
        return if (tokenPref?.get() != null) {
            tokenPref.get()
        } else {
            FirebaseInstanceId.getInstance().token ?: ""
        }
    }
}