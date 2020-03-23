package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterSecondOpinionAPI
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionGeneralAnswerBody
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionSpecialityAnswerBody
import com.ocs.sequre.domain.entity.*
import com.ocs.sequre.presentation.preference.SecondOpinionPreference
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SecondOpinionViewModel @Inject constructor(
    private val api: RequesterSecondOpinionAPI,
    private val preference: SecondOpinionPreference,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {

    private var id: Int = 1

    fun specialities(): Single<List<Speciality>> {
        return api.specialities()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun painQuestions(): Single<List<Question>> {
        return api.painQuestions(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun generalQuestions(): Single<List<Question>> {
        return api.generalQuestions(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun post(body: SecondOpinionBody): Single<SecondOpinion> {
        return api.post(body)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess { preference.set(it.id) }
    }

    fun put(body: SecondOpinionSpecialityAnswerBody): Completable {
        return api.put(preference.get(), body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun put(body: SecondOpinionGeneralAnswerBody): Completable {
        return api.put(preference.get(), body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun doctors(id: Int): Single<List<Doctor>> {
        return api.doctors(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun getReport(reportId: Int): Single<Report> {
        return api.getReport(reportId)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }
}