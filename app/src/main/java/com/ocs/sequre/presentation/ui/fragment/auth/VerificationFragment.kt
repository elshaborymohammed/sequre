package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.helper.PhoneAuthHelper
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_auth_verification.*
import kotlinx.android.synthetic.main.fragment_auth_verification.view.*

class VerificationFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_verification
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        arguments?.let {
            VerificationFragmentArgs.fromBundle(it).user
        }?.let { usr ->
            view.body.text =
                HtmlCompat.fromHtml(
                    getString(
                        R.string.verification_welcome_message,
                        "${usr.countryCode + usr.phone}"
                    ),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )

            try_resend.setOnClickListener {
                PhoneAuthHelper.setupVerifyPhoneNumber(
                    usr.countryCode,
                    usr.phone,
                    requireActivity()
                )
            }
            otp_code.addOnOTPCompleteListener { view, otp ->
                view.isError = false
                try {
                    PhoneAuthHelper.verifyPhoneNumberWithCode(
                        PhoneAuthHelper.verificationId,
                        requireActivity(),
                        otp, {
                            viewModel.register(usr)
                                .subscribe({
                                    findNavController().navigate(R.id.action_verificationFragment_to_navigationFragment)
                                }, Throwable::printStackTrace)
                        }, Throwable::printStackTrace
                    )
                } catch (e: IllegalArgumentException) {
                    Snackbar.make(
                        requireView(),
                        "The format of the phone number provided is incorrect",
                        Snackbar.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    onIOException()
                }
            }

            try_resend.performClick()
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading)
        )
    }
}