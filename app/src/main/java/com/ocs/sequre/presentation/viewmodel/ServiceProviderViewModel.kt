package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.domain.entity.ServiceProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ServiceProviderViewModel @Inject constructor(
    private val schedulers: RxCompactSchedulers
) : CompactDataViewModel<List<ServiceProvider>>() {

    override fun subscription(): Disposable {
        return get().compose(composeLoadingSingle()).subscribe(onSuccess(), onError())
    }

    fun get(): Single<List<ServiceProvider>> {
        val list = ArrayList<ServiceProvider>()
        for (i in 1..10) {
            list.add(
                ServiceProvider(
                    i,
                    "$i name",
                    "$i Lorem Ipsum is simply dummy text of the printing.",
                    "$i phone",
                    "$i photo"
                )
            )
        }

        return Single.just(list)
            .compose(schedulers.applyOnSingle())
    }
}