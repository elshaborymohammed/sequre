package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterAuthApi
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.data.remote.model.request.auth.Resend
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.domain.entity.Registration
import com.ocs.sequre.presentation.preference.AuthPreference
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val api: RequesterAuthApi,
    private val preference: AuthPreference,
    private val compose: RxCompactSchedulers
) : CompactViewModel() {

    fun login(phone: String, password: String): Single<AuthModel> {
        return api.login(Login(phone = phone, password = password))
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess { preference.set(it.token) }
    }

    fun register(body: Registration): Single<AuthModel> {
        return api.register(body)
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess { preference.set(it.token) }
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

    fun resend(body: Resend): Single<Void> {
        return api.resend(body)
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
    }

    fun countries(): Single<Void> {
        return api.countries()
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
    }
}