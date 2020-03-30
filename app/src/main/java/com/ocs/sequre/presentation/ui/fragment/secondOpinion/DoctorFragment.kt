package com.ocs.sequre.presentation.ui.fragment.secondOpinion

import android.util.Log
import android.view.View
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Doctor
import com.ocs.sequre.domain.entity.DoctorDetails
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable


class DoctorFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    private lateinit var doctor: Doctor

    override fun layoutRes(): Int = R.layout.fragment_doctor

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        arguments?.let {
            doctor = DoctorFragmentArgs.fromBundle(it).doctor
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.doctorDetails(doctor.id!!)
                .subscribe({ initViews(it) },
                    { onError(it.message ?: "") })
        )
    }

    private fun initViews(it: DoctorDetails?) {
        Log.e("DoctorFragment", it.toString())
    }

}