package com.ocs.sequre.presentation.ui.fragment.auth

import android.util.Patterns
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.helper.PhoneAuthHelper
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_auth_verification.*
import kotlinx.android.synthetic.main.fragment_auth_verification.view.*
import java.util.concurrent.TimeUnit

class VerificationFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel
    private val countDown: Long = 60L

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

            Patterns.PHONE

            resend.setOnClickListener {
                timer()
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

            resend.performClick()
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading)
        )
    }

    private fun timer() {
        subscribe(
            Flowable.intervalRange(1, countDown, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    resend.visibility = View.GONE
                    view?.try_resend?.text = getString(R.string.try_resend_code, countDown)
                }
                .map { countDown - it }
                .subscribe({
                    view?.try_resend?.text = getString(R.string.try_resend_code, it)
                }, Throwable::printStackTrace, {
                    resend.visibility = View.VISIBLE
                })
        )
    }
}