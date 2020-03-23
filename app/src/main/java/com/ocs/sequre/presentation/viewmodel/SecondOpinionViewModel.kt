package com.ocs.sequre.presentation.viewmodel

import com.compact.app.viewmodel.CompactViewModel
import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.data.remote.api.RequesterProfileAPI
import com.ocs.sequre.data.remote.api.RequesterSecondOpinionAPI
import com.ocs.sequre.domain.entity.*
import io.reactivex.Single
import javax.inject.Inject

class SecondOpinionViewModel @Inject constructor(
    private val profileAPI: RequesterProfileAPI,
    private val secondOpinionAPI: RequesterSecondOpinionAPI,
    private val schedulers: RxCompactSchedulers
) : CompactViewModel() {

    fun dependents(): Single<List<Dependent>> {
        return profileAPI.get()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data.dependents }
    }

    fun specialities(): Single<List<Speciality>> {
        return secondOpinionAPI.specialities()
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun painQuestions(id: Int): Single<List<Question>> {
        return secondOpinionAPI.painQuestions(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun generalQuestions(id: Int): Single<List<Question>> {
        return secondOpinionAPI.generalQuestions(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun doctors(id: Int): Single<List<Doctor>> {
        return secondOpinionAPI.doctors(id)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }

    fun getReport(reportId: Int): Single<Report> {
        return secondOpinionAPI.getReport(reportId)
            .compose(schedulers.applyOnSingle())
            .compose(composeLoadingSingle())
            .map { it.data }
    }
}