package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.jakewharton.rxrelay2.BehaviorRelay
import com.ocs.sequre.data.remote.api.RequesterProfileAPI
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.domain.entity.Profile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val api: RequesterProfileAPI,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {
    private var error: BehaviorRelay<Throwable> = BehaviorRelay.create()
    private var profile: BehaviorRelay<Profile> = BehaviorRelay.create()
    private var dependents: BehaviorRelay<List<Dependent>> = BehaviorRelay.create()

    fun call() {
        subscribe(
            get().subscribe(profile::accept, error::accept)
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

    fun profile(): Observable<Profile> {
        return profile.compose(schedulers.applyOnObservable())
    }

    fun dependents(): Observable<List<Dependent>> {
        return dependents.compose(schedulers.applyOnObservable())
    }

    fun error(): Observable<Throwable> {
        return error.compose(schedulers.applyOnObservable())
    }
}