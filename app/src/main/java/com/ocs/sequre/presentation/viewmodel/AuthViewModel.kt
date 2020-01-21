package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterAuthApi
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.request.auth.Login
import com.ocs.sequre.data.remote.model.request.auth.Resend
import com.ocs.sequre.data.remote.model.request.auth.Verification
import com.ocs.sequre.data.remote.model.response.success.AccessToken
import com.ocs.sequre.domain.entity.User
import io.reactivex.Single
import retrofit2.http.Body
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val api: RequesterAuthApi,
    private val compose: RxCompactSchedulers
) : CompactViewModel() {

    fun login(mobile: String, password: String): Single<AccessToken> {
        return api.login(Login(mobile, password))
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
    }

    fun register(body: User): Single<AccessToken> {
        return api.register(body)
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
    }

    fun check(body: AuthValidation): Single<Void> {
        return api.check(body)
            .compose(compose.applyOnSingle())
            .compose(composeLoadingSingle())
    }

    fun resend(body: Resend?): Single<Void> {
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