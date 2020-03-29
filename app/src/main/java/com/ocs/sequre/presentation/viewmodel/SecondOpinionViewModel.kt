package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.jakewharton.rxrelay2.BehaviorRelay
import com.ocs.sequre.data.remote.api.RequesterSecondOpinionAPI
import com.ocs.sequre.domain.entity.*
import com.ocs.sequre.presentation.preference.SecondOpinionPreference
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SecondOpinionViewModel @Inject constructor(
    private val api: RequesterSecondOpinionAPI,
    private val preference: SecondOpinionPreference,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {
    val showSpeciality = BehaviorRelay.create<Any>()

    fun showSpeciality(): Observable<Any> {
        return showSpeciality.observeOn(AndroidSchedulers.mainThread())
    }

    var body = SecondOpinion.Body.Data()
    var speciality = SecondOpinion.Body.SpecialityAnswer()
    var general = SecondOpinion.Body.GeneralAnswer()

    fun post(body: SecondOpinion.Body.Data): Single<SecondOpinion.Response> {
        body.date = "2020-1-1"
        return api.post(body)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
            .doOnSuccess {
                body.id = it.id
                preference.set(body)
            }
    }

    fun put(body: SecondOpinion.Body.SpecialityAnswer): Completable {
        return api.put(preference.get().id!!, body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun put(body: SecondOpinion.Body.GeneralAnswer): Completable {
        return api.put(preference.get().id!!, body)
            .compose(schedulers.applyOnCompletable())
            .compose(composeLoadingCompletable())
    }

    fun specialities(): Single<List<Speciality>> {
        return api.specialities()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun painQuestions(): Single<List<Question>> {
        return api.painQuestions(preference.get().painId!!)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun generalQuestions(): Single<List<Question>> {
        return api.generalQuestions(preference.get().painId!!)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
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