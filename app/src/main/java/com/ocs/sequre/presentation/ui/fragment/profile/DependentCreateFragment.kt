package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class DependentCreateFragment : DependentFragment() {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "New Dependent"
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.create(it).subscribe(::onSuccess, onError())
        )
    }
}