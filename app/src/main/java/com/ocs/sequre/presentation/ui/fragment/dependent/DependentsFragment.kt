package com.ocs.sequre.presentation.ui.fragment.dependent

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.app.extensions.setVisibility
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.DependentAdapter
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dependents.view.*
import kotlinx.android.synthetic.main.layout_list_items.view.*

class DependentsFragment : BaseFragment() {

    private lateinit var adapter: DependentAdapter
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_dependents
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)
        adapter = DependentAdapter()
        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.dependentUpdateFragment,
                DependentUpdateFragmentArgs(
                    it
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
                }
            }, ::print)
        )
    }
}