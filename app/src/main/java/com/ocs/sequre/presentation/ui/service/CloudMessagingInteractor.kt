package com.ocs.sequre.presentation.ui.service

import com.ocs.sequre.data.remote.api.RequesterAuthAPI
import com.ocs.sequre.data.remote.model.request.auth.DeviceToken
import com.ocs.sequre.presentation.preference.AuthPreference
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CloudMessagingInteractor
@Inject
constructor(
    private val api: RequesterAuthAPI,
    private val auth: AuthPreference
) {
    fun refreshDeviceToken(token: String) {
        if (auth.hasToken()) {
            api.updateDeviceToken(DeviceToken(token))
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }
}