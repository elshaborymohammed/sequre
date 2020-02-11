package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterDependentAPI
import com.ocs.sequre.domain.entity.Dependent
import io.reactivex.Completable
import javax.inject.Inject

class DependentViewModel @Inject constructor(
    private val api: RequesterDependentAPI,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {

    fun create(body: Dependent): Completable {
        return api.create(body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun update(id: Int, body: Dependent): Completable {
        return api.update(id, body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun delete(id: Int): Completable {
        return api.delete(id)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }
}