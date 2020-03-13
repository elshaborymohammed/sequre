package com.ocs.sequre.presentation.ui.fragment.profile.dependent

import android.view.View
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class DependentUpdateFragment : DependentFragment(0, 0) {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "Edit Dependent"
//        view.update.isEnabled = true
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.update(it.id, it).subscribe(::onSuccess, onError())
        )
    }

    override fun onDataLoaded() {
        DependentUpdateFragmentArgs.fromBundle(
            requireArguments()
        ).data?.apply {
            viewHolder.set(this)
            requireView().delete.visibility = View.VISIBLE
        }
    }
}