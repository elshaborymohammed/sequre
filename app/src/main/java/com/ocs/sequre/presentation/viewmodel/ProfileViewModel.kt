package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterProfileAPI
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.domain.entity.Profile
import com.ocs.sequre.domain.entity.ProfileData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val api: RequesterProfileAPI,
    private val schedulers: RxCompactSchedulers
) : CompactDataViewModel<ProfileData>() {

    override fun call() {
        addDisposable(
            get().subscribe(data::accept, Throwable::printStackTrace)
        )
    }

    private fun get(): Single<ProfileData> {
        return api.get()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun dependents(): Observable<List<Dependent>> {
        return data().map { it.dependents }
    }

    fun update(body: ProfileData): Completable {
        return api.update(body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }
}