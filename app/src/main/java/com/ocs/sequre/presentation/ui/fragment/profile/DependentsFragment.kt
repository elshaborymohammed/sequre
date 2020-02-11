package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.base.loading
import com.ocs.sequre.presentation.ui.adapter.DependentAdapter
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_list_items.view.*

class DependentsFragment : BaseFragment() {

    private lateinit var adapter: DependentAdapter
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_list_of_items
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(ProfileViewModel::class.java)
        adapter = DependentAdapter()
        view.list_item.adapter = adapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(requireView().progress::loading),
            viewModel.dependents().subscribe(adapter::swap, ::print)
        )
    }
}