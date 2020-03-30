package com.ocs.sequre.presentation.ui.fragment.secondOpinion

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Doctor
import com.ocs.sequre.domain.entity.DoctorDetails
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_doctor.*


class DoctorFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    private lateinit var doctor: Doctor

    override fun layoutRes(): Int = R.layout.fragment_doctor

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)
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
        GlideApp.with(this)
            .load(it?.logo)
            .into(toolbarImage)
        with(collapsingToolbar) {
            title = it?.name
            setCollapsedTitleTextColor(colorBlack())
            setExpandedTitleColor(colorBlack())
            setExpandedTitleTextAppearance(R.style.App_TextAppearance_Headline)
        }
        brief.text = it?.brief
    }

    private fun colorBlack(): Int {
        return ContextCompat.getColor(
            activity!!,
            R.color.black
        )
    }
}