package com.ocs.sequre.presentation.ui.fragment.profile

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.ocs.sequre.domain.entity.Dependent
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.*
import kotlinx.android.synthetic.main.layout_user_profile_data.*

class DependentCreateFragment : DependentFragment() {

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.title.text = "New Dependent"

//        view.apply {
//            input_relationship.editText?.setText("Daughter")
//            input_name.editText?.setText("Daughter Name")
//            input_email.editText?.setText("name@email.com")
//            input_phone.editText?.setText("012345678")
//            input_gender.editText?.setText("Female")
//            input_birth_date.editText?.setText("2020/1/11")
//        }
    }

    override fun onSaveClicked(it: Dependent) {
        subscribe(
            dependentViewModel.create(it).subscribe(::onSuccess, onError())
        )
    }
}