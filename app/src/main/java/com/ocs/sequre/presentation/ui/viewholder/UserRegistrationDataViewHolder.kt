package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.compact.app.extensions.*
import com.jakewharton.rxbinding3.view.focusChanges
import com.ocs.sequre.R
import com.ocs.sequre.domain.entity.Country
import com.ocs.sequre.domain.entity.Registration
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_user_create_account.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*

class UserRegistrationDataViewHolder constructor(private val view: View) {
    private val name: Observable<Boolean>
        get() = view.input_name.fullName()
    private val phone: Observable<Boolean>
        get() = view.input_phone.phone()
    private val email: Observable<Boolean>
        get() = view.input_email.email()
    private val password: Observable<Boolean>
        get() = view.input_password.password()
    private val passwordConfirm: Observable<Boolean>
        get() = view.input_password_confirm.confirmPassword(view.input_password)

    private val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    private val phoneFocusChanges: Observable<Boolean>
        get() = view.input_phone.editText!!.focusChanges()

    fun setCountries(it: List<Country>) {
        view.input_country.apply {
            (editText as AutoCompleteTextView).run {
                threshold = 1 //will start working from first character
                ArrayAdapter(
                    view.context,
                    R.layout.select_dialog_item,
                    R.id.text,
                    it
                ).apply {
                    setAdapter(this)
                    setOnItemClickListener { parent, view, position, id ->
                        text(it[position].code)
                    }
                }
                text("+20")
            }
        }
    }

    fun get(): Registration {
        return Registration(
            name = view.input_name.text(),
            email = view.input_email.text(),
            countryCode = view.input_country.text(),
            phone = view.input_phone.text(),
            password = view.input_password.text()
        )
    }

    fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
                name,
                email,
                phone,
                password,
                passwordConfirm,
                Function5 { name: Boolean, email: Boolean, phone: Boolean, password: Boolean, passwordConfirm: Boolean ->
                    name && email && phone && password && passwordConfirm
                }
            ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun emailValidation(block: ((email: String) -> Unit)): Observable<Boolean> =
        Observable.combineLatest(
                email,
                emailFocusChanges,
                BiFunction { t1: Boolean, t2: Boolean -> t1 && !t2 })
            .distinctUntilChanged()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it) block(view.input_email.text()) }

    private fun phoneValidation(block: ((phone: String) -> Unit)): Observable<Boolean> =
        Observable.combineLatest(
                phone,
                phoneFocusChanges,
                BiFunction { t1: Boolean, t2: Boolean -> t1 && !t2 }
            ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it) block(view.input_phone.text()) }

    fun validationData(
        email: ((email: String) -> Unit),
        phone: ((phone: String) -> Unit)
    ): Observable<Boolean> {
        return Observable.combineLatest(
                emailValidation(email),
                phoneValidation(phone),
                BiFunction { email: Boolean, phone: Boolean -> email && phone })
            .distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }
}