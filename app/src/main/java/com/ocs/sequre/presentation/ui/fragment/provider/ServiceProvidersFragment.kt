package com.ocs.sequre.presentation.ui.fragment.provider

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.ServiceProviderAdapter
import com.ocs.sequre.presentation.viewmodel.ServiceProviderViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_list_items.view.*

class ServiceProvidersFragment : BaseFragment() {

    private lateinit var adapter: ServiceProviderAdapter
    private lateinit var viewModel: ServiceProviderViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_list_of_items
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(ServiceProviderViewModel::class.java)
        adapter = ServiceProviderAdapter()
        view.list_item.adapter = adapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.data().subscribe(adapter::swap, ::print)
        )
    }
}