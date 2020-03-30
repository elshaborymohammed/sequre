package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.jakewharton.rxrelay2.BehaviorRelay
import com.ocs.sequre.data.remote.api.RequesterMedicalDocumentAPI
import com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody
import com.ocs.sequre.domain.entity.MedicalDocument
import com.ocs.sequre.presentation.preference.SecondOpinionPreference
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MedicalDocumentViewModel @Inject constructor(
    private val api: RequesterMedicalDocumentAPI,
    private val preference: SecondOpinionPreference,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {
    val reload = BehaviorRelay.create<Any>()

    fun post(body: MedicalDocumentBody): Completable {
        body.opinionId = preference.get().id
        return api.post(body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun delete(id: Int, category: Int): Completable {
        return api.delete(id, category)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun get(): Single<MedicalDocument> {
        return api.get(preference.get().id!!)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }
}