package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.navigation.Navigation
import com.compact.app.extensions.loginName
import com.compact.app.extensions.password
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_auth_sign_in.view.*

class SignInFragment : BaseFragment() {


    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_in
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.sign_in.isEnabled = false

        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        view.sign_in.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_signInFragment_to_navigationFragment)
        )

        view.create_account.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_signInFragment_to_signUpFragment)
        )

        val loginName = view.input_auth_name.loginName()
        val password = view.input_auth_password.password()

        Observable.combineLatest(
            loginName,
            password,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
        ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.sign_in.isEnabled = it }
    }
}