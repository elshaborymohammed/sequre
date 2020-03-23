package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterNotifications
import com.ocs.sequre.domain.entity.Notification
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 *Created by marco on 2020-03-18
 */
class NotificationsViewModel
@Inject
constructor(
    private val schedulers: RxCompactSchedulers,
    private val api: RequesterNotifications
) : CompactDataViewModel<List<Notification>>() {

    override fun subscription(): Disposable {
        return all().compose(composeLoadingSingle()).subscribe(onSuccess(), onError())
    }

    fun all(): Single<List<Notification>> {
        return api.all()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }
}