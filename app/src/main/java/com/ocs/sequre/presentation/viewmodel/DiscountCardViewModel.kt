package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactDataViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.domain.entity.DiscountCard
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
}