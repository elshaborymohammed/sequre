package com.ocs.sequre.presentation.ui.fragment.auth

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import androidx.core.text.HtmlCompat
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.helper.PhoneAuthHelper
import com.ocs.sequre.domain.entity.User
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import kotlinx.android.synthetic.main.fragment_auth_verification.*
import kotlinx.android.synthetic.main.fragment_auth_verification.view.*


class VerificationFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_verification
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        arguments?.let {
            //            VerificationFragmentArgs.fromBundle(it).user
            User(_mobile = "01019992577", countryCode = "+20")
        }?.let {
            view.body.text =
                HtmlCompat.fromHtml(
                    getString(
                        R.string.verification_welcome_message,
                        "${it.countryCode + it.mobile}"
                    ),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )

            PhoneAuthHelper.setupVerifyPhoneNumber(it.countryCode, it.mobile, requireActivity())
            otp_code.addOnOTPCompleteListener { view, otp ->
                view.isError = true
                PhoneAuthHelper.verifyPhoneNumberWithCode(
                    PhoneAuthHelper.verificationId,
                    requireActivity(),
                    otp
                )
            }
        }
    }
}