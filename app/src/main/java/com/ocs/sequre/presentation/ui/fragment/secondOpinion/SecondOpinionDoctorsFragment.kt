package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.DoctorsAdapter
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_doctors.view.*
import javax.inject.Inject

/**
 *Created by marco on 2020-03-21
 */
class SecondOpinionDoctorsFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    @field:[Inject]
    lateinit var mAdapter: DoctorsAdapter
    private var cId = 1

    override fun layoutRes(): Int = R.layout.fragment_second_opinion_doctors

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)
        mAdapter.setOnItemClickListener {}
        view.recyclerView.adapter = mAdapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel
                .doctors(cId)
                .subscribe(
                    { mAdapter.swap(it) },
                    onError()
                )
        )
    }

    override fun onError(message: String) {
        super.onError(message)
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}