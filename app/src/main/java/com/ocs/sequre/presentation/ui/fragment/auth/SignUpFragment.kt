package com.ocs.sequre.presentation.ui.fragment.auth

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.presentation.ui.viewholder.RegistrationDataViewHolder
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_auth_sign_up.view.*

class SignUpFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var signUpViewHolder: RegistrationDataViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_up
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        signUpViewHolder = RegistrationDataViewHolder(view)

        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        view.next.setOnClickListener {
            viewModel.check(
                AuthValidation(
                    mobile = signUpViewHolder.get().mobile,
                    email = signUpViewHolder.get().email
                )
            ).subscribe({
                findNavController().navigate(
                    R.id.action_signUpFragment_to_verificationFragment,
                    VerificationFragmentArgs(signUpViewHolder.get()).toBundle()
                )
            }, onError())
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe { if (it) loadingOn() else loadingOff() },
            signUpViewHolder.validations().subscribe(
                { requireView().next.isEnabled = it },
                Throwable::printStackTrace
            ),
            signUpViewHolder.validations().subscribe(
                { requireView().next.isEnabled = it },
                Throwable::printStackTrace
            )
        )
    }

    override fun onApiException(code: ErrorStatus, errors: List<Error>) {
        if (code != ErrorStatus.VALIDATION) {
            var input: TextInputLayout
            for (e in errors) {
                input = view!!.findViewWithTag(e.path)
                input.error = e.message
                input.isErrorEnabled = true
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        lifecycle.removeObserver(signUpViewHolder)
    }
}