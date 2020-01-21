package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import com.compact.app.CompactDialogFragment
import com.ocs.sequre.R
import com.ocs.sequre.presentation.preference.AuthPreference
import kotlinx.android.synthetic.main.fragment_auth_sign_out.*
import javax.inject.Inject

class SignOutFragment : CompactDialogFragment() {

    @Inject
    lateinit var auth: AuthPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_out
    }

    override fun onViewBound(view: View) {
        cancel.setOnClickListener { dismissAllowingStateLoss() }
        logout.setOnClickListener { auth.clear() }
    }
}