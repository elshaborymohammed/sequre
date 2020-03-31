package com.ocs.sequre.presentation.ui.fragment.dependent

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.presentation.ui.adapter.DependentSummeryAdapter
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_list_items.view.*

class DependentsSummeryFragment : BaseBottomSheet() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var secondOpinionViewModel: SecondOpinionViewModel
    private lateinit var adapter: DependentSummeryAdapter

    override fun layoutRes(): Int {
        return R.layout.fragment_dependents_summary
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        profileViewModel =
            ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)
        secondOpinionViewModel =
            ViewModelProvider(requireActivity(), factory).get(SecondOpinionViewModel::class.java)

        adapter = DependentSummeryAdapter()
        adapter.setOnItemClickListener {
            secondOpinionViewModel.body.dependentId = it.id
            dismiss()
            secondOpinionViewModel.showSpeciality.accept("")
        }
        view.list_item.adapter = adapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            profileViewModel.loading().subscribe(::loading),
            profileViewModel.dependents().subscribe(adapter::swap, onError())
        )
    }
}