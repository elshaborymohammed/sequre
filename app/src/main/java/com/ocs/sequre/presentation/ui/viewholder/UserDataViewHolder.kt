package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import com.compact.app.extensions.*
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.textChanges
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import io.reactivex.functions.Function6
import kotlinx.android.synthetic.main.fragment_auth_sign_up.view.*
import kotlinx.android.synthetic.main.layout_user_data.view.*

class UserDataViewHolder constructor(private val view: View) {
    private var disposable: Disposable

    init {
        view.next.isEnabled = false
        val emailFocusChanges = view.input_email.editText!!.focusChanges()
        val phoneFocusChanges = view.input_mobile.editText!!.focusChanges()

        val name = view.input_name.notNullOrEmpty()
        val phone = view.input_mobile.phone()
        val email = view.input_email.email()
        val password = view.input_password.password()
        val passwordConfirm = view.input_password_confirm.confirmPassword(view.input_password)

        Observable.combineLatest(
            email,
            emailFocusChanges,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
        ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.next.isEnabled = it }, Throwable::printStackTrace)

        Observable.combineLatest(
            phone,
            phoneFocusChanges,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
        ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.next.isEnabled = it }, Throwable::printStackTrace)

        disposable = Observable.combineLatest(
            name,
            email,
            phone,
            password,
            passwordConfirm,
            Function5 { name: Boolean, email: Boolean, mobile: Boolean, password: Boolean, passwordConfirm: Boolean ->
                name && email && mobile && password && passwordConfirm
            }
        ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.next.isEnabled = it }, Throwable::printStackTrace)
    }

    fun set(obj: User) {
        view.input_country.setSelection(0, true)
        view.input_mobile.editText?.setText(obj.mobile)
        view.input_name.editText?.setText(obj.name)
        view.input_email.editText?.setText(obj.email)
        view.input_password.editText?.setText(obj.password)
        view.input_password_confirm.editText?.setText(obj.password)
    }

    fun get(): User {
        return User(
            name = view.input_name.editText?.text.toString(),
            email = view.input_email.editText?.text.toString(),
            password = view.input_password.editText?.text.toString(),
            mobile = view.input_mobile.editText?.text.toString(),
            countryCode = view.resources.getStringArray(R.array.country_code_array)[view.input_country.selectedItemPosition]
        )
    }
}