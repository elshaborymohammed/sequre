package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
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

    fun post(body: MedicalDocumentBody): Completable {
        body.opinionId = preference.get()
        return api.post(body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun delete(id: Int, category: Int): Completable {
        return api.delete(id, category)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun get(body: Int): Single<MedicalDocument> {
        return api.get(body)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }
}