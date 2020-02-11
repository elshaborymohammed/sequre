package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.jakewharton.rxrelay2.BehaviorRelay
import com.ocs.sequre.data.remote.api.RequesterProfileAPI
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.domain.entity.Profile
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val api: RequesterProfileAPI,
    private val schedulers: RxCompactSchedulers
) : CompactDataViewModel<Profile>() {
    private var dependents: BehaviorRelay<List<Dependent>> = BehaviorRelay.create()

    override fun call() {
        addDisposable(
            get().subscribe(data::accept, Throwable::printStackTrace)
        )
    }

    private fun get(): Single<Profile> {
        return api.get()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .doOnSuccess { it.data.dependents?.let { t -> dependents.accept(t) } }
            .map { it.data as Profile }
    }

    fun update(body: Profile): Completable {
        return api.update(body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun dependents(): BehaviorRelay<List<Dependent>> {
        return dependents
    }
}