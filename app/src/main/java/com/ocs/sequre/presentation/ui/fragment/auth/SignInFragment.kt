package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.compact.app.extensions.loginName
import com.compact.app.extensions.password
import com.compact.app.extensions.text
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.response.error.ResponseError
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import com.smart.compact.response.ApiException
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_auth_sign_in.view.*
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var loginName: Observable<Boolean>
    private lateinit var password: Observable<Boolean>

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_in
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        view.sign_in.isEnabled = false

        view.sign_in.setOnClickListener {
            viewModel.login(
                view.input_auth_name.text().toString(),
                view.input_auth_password.text().toString()
            ).subscribe({
                findNavController().navigate(R.id.action_signInFragment_to_navigationFragment)
            }, onError())
        }

        view.create_account.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_signInFragment_to_signUpFragment)
        )

        loginName = view.input_auth_name.loginName()!!
        password = view.input_auth_password.password()!!
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe { if (it) loadingOn() else loadingOff() },
            Observable.combineLatest(
                loginName,
                password,
                BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
            ).distinctUntilChanged()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.sign_in!!.isEnabled = it }
        )
    }
}
