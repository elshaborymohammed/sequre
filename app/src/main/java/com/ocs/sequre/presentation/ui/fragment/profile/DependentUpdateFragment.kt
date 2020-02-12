package com.ocs.sequre.presentation.ui.fragment.profile

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class DependentUpdateFragment : DependentFragment() {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "Edit Dependent"

        DependentUpdateFragmentArgs.fromBundle(requireArguments()).data?.apply {
            viewHolder.set(this)
        }
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.update(it.id, it).subscribe(::onSuccess, onError())
        )
    }
}