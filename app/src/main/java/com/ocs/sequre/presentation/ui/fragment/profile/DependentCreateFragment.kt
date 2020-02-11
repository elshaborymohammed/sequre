package com.ocs.sequre.presentation.ui.fragment.profile

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class DependentCreateFragment : DependentFragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "New Dependent"
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.create(it).subscribe({ onSuccess(it) }, onError())
        )
    }
}