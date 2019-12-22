package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_personal_info.*
import kotlinx.android.synthetic.main.layout_saved_address.*

class ProfileFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)


        personal_info_edit.setOnClickListener {
            val profile = EditProfileFragment()
            profile.show(requireFragmentManager(), null)
        }

        saved_address_edit.setOnClickListener {
            val profile = EditAddressFragment()
            profile.show(requireFragmentManager(), null)
        }

        change_password.setOnClickListener {
            val profile = ChangePasswordFragment()
            profile.show(requireFragmentManager(), null)
        }
    }
}