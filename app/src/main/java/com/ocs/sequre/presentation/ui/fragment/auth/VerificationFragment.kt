package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.helper.PhoneAuthHelper
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth_verification.*
import kotlinx.android.synthetic.main.fragment_auth_verification.view.*
import java.lang.Exception

class VerificationFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_verification
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        arguments?.let {
            VerificationFragmentArgs.fromBundle(it).user
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
                view.isError = false
                try {
                    PhoneAuthHelper.verifyPhoneNumberWithCode(
                        PhoneAuthHelper.verificationId,
                        requireActivity(),
                        otp, {
                            viewModel.register(it)
                                .subscribe({
                                    findNavController().navigate(R.id.action_verificationFragment_to_navigationFragment)
                                }, Throwable::printStackTrace)
                        }, Throwable::printStackTrace
                    )
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(
                        context,
                        "The format of the phone number provided is incorrect",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Connection Lost", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}