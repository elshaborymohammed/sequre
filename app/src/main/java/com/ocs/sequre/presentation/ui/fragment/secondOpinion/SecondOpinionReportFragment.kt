package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Notification
import com.ocs.sequre.domain.entity.Report
import com.ocs.sequre.presentation.ui.fragment.secondopinion.SecondOpinionReportFragmentArgs
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_report.*

/**
 *Created by marco on 2020-03-19
 */
class SecondOpinionReportFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    private lateinit var notification: Notification

    override fun layoutRes(): Int = R.layout.fragment_report

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)
        notification = SecondOpinionReportFragmentArgs.fromBundle(
            requireArguments()
        )
            .notification

        btnDone.setOnClickListener { Navigation.findNavController(view).navigateUp() }
    }

    override fun subscriptions(): Array<Disposable> {
        notification.id?.let {
            viewModel.getReport(it)
                .subscribe(::initViews)
                { error -> error.message?.let { it1 -> onError(it1) } }
        }
        return arrayOf(viewModel.loading().subscribe(::loading))
    }

    private fun initViews(report: Report) {
        patientName.text = report.patientName
        date.text = report.startDate
        doctorName.text = report.doctorName
        diagnosis.text = report.diagnosis
        dosage.text = report.dosage
        startDate.text = report.startDate
        comments.text = report.comment
    }
}