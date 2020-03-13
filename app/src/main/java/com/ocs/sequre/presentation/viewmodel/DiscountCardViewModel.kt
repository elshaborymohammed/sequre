package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.domain.entity.DiscountCard
import com.ocs.sequre.domain.entity.Offer
import io.reactivex.Single
import javax.inject.Inject

class DiscountCardViewModel @Inject constructor(
    private val schedulers: RxCompactSchedulers
) : CompactDataViewModel<List<DiscountCard>>() {

    override fun call() {
        val list = ArrayList<DiscountCard>()
        for (i in 1..6) {
            list.add(DiscountCard(i, "name $i", (i * 1000).toDouble()))
        }

        data.accept(list)
    }

    fun offers(): Single<List<Offer>> {
        val list = ArrayList<Offer>()
        for (i in 1..6) {
            list.add(
                Offer(
                    i,
                    "name $i",
                    "$i Lorem Ipsum is simply dummy text of the printing and typesetting industry. LoremIpsum has been the industry's standard dummy text ever since the 1500sare like Aldus PageMaker including versions of Lorem Ipsum."
                )
            )
        }

        return Single.just(list)
            .compose(schedulers.applyOnSingle())
    }
}