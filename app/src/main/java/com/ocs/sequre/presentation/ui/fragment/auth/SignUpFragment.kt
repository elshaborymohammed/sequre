package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.compact.app.extensions.error
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.data.remote.model.response.error.ResponseErrors
import com.ocs.sequre.presentation.ui.viewholder.UserDataViewHolder
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import com.smart.compact.response.ApiException
import kotlinx.android.synthetic.main.fragment_auth_sign_up.view.*
import kotlinx.android.synthetic.main.layout_user_data.view.*

class SignUpFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var signUpViewHolder: UserDataViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_up
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        signUpViewHolder = UserDataViewHolder(view)

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
            }, {
                if (it is ApiException) {
                    it.error(ResponseErrors::class.java)?.run {
                        if (code == ErrorStatus.VALIDATION) {
                            var input: TextInputLayout
                            for (e in errors) {
                                input = view.findViewWithTag(e.path)
                                input.error = e.message
                                input.isErrorEnabled = true
                            }
                        }
                    }
                } else {
                    super.onError().accept(it)
                }
            })
        }

        view.sign_in.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_signUpFragment_to_signInFragment
            )
        )
    }
}