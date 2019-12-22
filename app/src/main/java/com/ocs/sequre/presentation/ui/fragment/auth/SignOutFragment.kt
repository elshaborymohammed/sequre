package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import com.compact.app.CompactDialogFragment
import com.ocs.sequre.R
import kotlinx.android.synthetic.main.fragment_auth_sign_out.*

class SignOutFragment : CompactDialogFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_out
    }

    override fun onViewBound(view: View) {

        cancel.setOnClickListener { dismissAllowingStateLoss() }
    }
}