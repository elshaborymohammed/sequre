package com.ocs.sequre.presentation.viewmodel

import android.util.Log
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
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val api: RequesterAuthAPI,
    private val preference: AuthPreference,
    private val compose: RxCompactSchedulers
) : CompactViewModel() {

    fun fcmToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                val newToken: String = instanceIdResult.token
                Log.e("newToken", newToken)
            }
    }

    fun login(phone: String, password: String): Single<AuthModel> {
        return api.login(
            Login(
                phone = phone,
                password = password,
                device_token = FirebaseInstanceId.getInstance().token ?: ""
            )
        )
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
}