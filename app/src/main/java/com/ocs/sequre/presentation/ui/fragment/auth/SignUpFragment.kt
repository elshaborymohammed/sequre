package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.app.extensions.text
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.presentation.ui.viewholder.UserRegistrationDataViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_auth_sign_up.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*

class SignUpFragment : BaseFragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewHolder: UserRegistrationDataViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_up
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.next.isEnabled = false
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewHolder = UserRegistrationDataViewHolder(view)

        view.next.setOnClickListener {
            viewModel.check(
                AuthValidation(
                    phone = viewHolder.get().phone,
                    email = viewHolder.get().email
                )
            ).subscribe({
                findNavController().navigate(
                    R.id.action_signUpFragment_to_verificationFragment,
                    VerificationFragmentArgs(viewHolder.get()).toBundle()
                )
            }, onError())
        }
        view.input_country.text("+20")
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewHolder.validations().subscribe(
                { requireView().next.isEnabled = it }, Throwable::printStackTrace
            ), countries()
            //,
//            signUpViewHolder.validationData({
//                subscribe(
//                    viewModel.checkEmail(it).subscribe(
//                        { Log.d("OkHttp", "validationData email") }, onError()
//                    )
//                )
//            }, {
//                subscribe(
//                    viewModel.checkPhone(it).subscribe(
//                        { Log.d("OkHttp", "phone:") }, onError()
//                    )
//                )
//            }).subscribe({ Log.d("OkHttp", "validationData $it") }, Throwable::printStackTrace)
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

    private fun countries(): Disposable = viewModel.countryCode()
        .subscribe({
            viewHolder.setCountries(it)
            view!!.input_country.apply {
                endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                setEndIconDrawable(R.drawable.ic_chevron_down)
            }
        }, {
            onError()
            view!!.input_country.apply {
                setEndIconDrawable(R.drawable.ic_chevron_down)
                endIconMode = TextInputLayout.END_ICON_CUSTOM
                setEndIconOnClickListener {
                    subscribe(countries())
                }
            }
        })
}