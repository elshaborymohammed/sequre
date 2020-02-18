package com.ocs.sequre.presentation.ui.fragment.auth

import android.content.Intent
import android.view.View
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.ui.activity.LaunchActivity
import kotlinx.android.synthetic.main.fragment_auth_sign_out.*
import javax.inject.Inject

class SignOutFragment : BaseBottomSheet() {

    @Inject
    lateinit var auth: AuthPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_out
    }

    override fun onViewBound(view: View) {
        cancel.setOnClickListener { dismissAllowingStateLoss() }
        ok.setOnClickListener {
            if (auth.clear()) {
                val intent = Intent(context, LaunchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}