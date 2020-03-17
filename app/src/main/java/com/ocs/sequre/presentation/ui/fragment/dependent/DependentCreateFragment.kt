package com.ocs.sequre.presentation.ui.fragment.dependent

import android.view.View
import com.compact.app.extensions.text
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*

class DependentCreateFragment : DependentFragment(1, 1) {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "New Dependent"
    }

    override fun onDataLoaded() {
        view?.input_country?.text("+20")
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.create(it).subscribe(::onSuccess, onError())
        )
    }
}