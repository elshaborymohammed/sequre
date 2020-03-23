package com.ocs.sequre.presentation.ui.fragment.dependent

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.app.extensions.setVisibility
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody
import com.ocs.sequre.presentation.ui.adapter.DependentSummeryAdapter
import com.ocs.sequre.presentation.ui.fragment.secondopinion.SecondOpinionChooseSpecialityFragmentArgs
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dependents.view.*
import kotlinx.android.synthetic.main.layout_list_items.view.*

class DependentsSummeryFragment : BaseBottomSheet() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: DependentSummeryAdapter

    override fun layoutRes(): Int {
        return R.layout.fragment_dependents_summary
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        adapter = DependentSummeryAdapter()
        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_dependentsSummeryFragment_to_secondOpinionChooseSpecialityFragment,
                SecondOpinionChooseSpecialityFragmentArgs(
                    SecondOpinionBody.FOR_OTHER,
                    it.id
                ).toBundle()
            )
        }
        view.list_item.adapter = adapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.dependents().subscribe({
                if (it.isNullOrEmpty()) {
                    requireView().empty.setVisibility(true)
                    requireView().list_item.setVisibility(false)
                } else {
                    requireView().empty.setVisibility(false)
                    requireView().list_item.setVisibility(true)
                    adapter.swap(it)
                    adapter.addAll(it)
                    adapter.addAll(it)
                    adapter.addAll(it)
                }
            }, ::print)
        )
    }
}