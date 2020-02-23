package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class DependentUpdateFragment : DependentFragment() {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "Edit Dependent"

        DependentUpdateFragmentArgs.fromBundle(requireArguments()).data?.apply {
            viewHolder.set(this)
            view.delete.visibility = View.VISIBLE
        }

        view.update.isEnabled = true
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            viewModel.update(it.id, it).subscribe(::onSuccess, onError())
        )
    }
}