package com.ocs.sequre.presentation.ui.fragment.notifications

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Notification.Companion.TYPE_OFFER
import com.ocs.sequre.domain.entity.Notification.Companion.TYPE_OPINION
import com.ocs.sequre.presentation.ui.adapter.NotificationsAdapter
import com.ocs.sequre.presentation.ui.fragment.secondOpinion.SecondOpinionReportFragmentArgs
import com.ocs.sequre.presentation.viewmodel.NotificationsViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import javax.inject.Inject

/**
 *Created by marco on 2020-03-17
 */
class NotificationsFragment : BaseFragment() {

    private lateinit var viewModel: NotificationsViewModel
    @field:[Inject]
    lateinit var mAdapter: NotificationsAdapter

    override fun layoutRes(): Int = R.layout.fragment_notifications

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(NotificationsViewModel::class.java)
        mAdapter.setOnItemClickListener {
            when (it.sourceType) {
                TYPE_OPINION -> findNavController().navigate(
                    R.id.action_notificationsFragment_to_secondOpinionReportFragment,
                    SecondOpinionReportFragmentArgs(it).toBundle()
                )
                TYPE_OFFER -> Toast.makeText(activity, "Offer Clicked", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(
                    activity,
                    "Notification Undefined",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
        view.recyclerView.adapter = mAdapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel
                .all()
                .subscribe(
                    {
                        mAdapter.swap(it)
                    }, {
                        onError(it.message ?: "")
                    }

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