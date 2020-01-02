package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class DataViewModel @Inject constructor(
    private val compose: RxCompactSchedulers
) : CompactDataViewModel<Int>() {

    override fun call() {
        Observable.create<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onError(Throwable("Error in rx"))
            it.onNext(4)
        }.compose(compose.applyOnObservable())
            .subscribe({
                data.accept(it)
            }, {
                it.printStackTrace()
//                data.doOnError { it }
            })
    }
}