package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.compact.app.extensions.*
import com.jakewharton.rxbinding3.view.focusChanges
import com.ocs.sequre.domain.entity.Registration
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_user_data.view.*

class RegistrationDataViewHolder constructor(private val view: View) {
    private val name: Observable<Boolean>
        get() = view.input_name.notNullOrEmpty()
    private val email: Observable<Boolean>
        get() = view.input_email.email()
    private val phone: Observable<Boolean>
        get() = view.input_mobile.phone()
    private val password: Observable<Boolean>
        get() = view.input_password.password()
    private val passwordConfirm: Observable<Boolean>
        get() = view.input_password_confirm.confirmPassword(view.input_password)

    private val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    private val phoneFocusChanges: Observable<Boolean>
        get() = view.input_mobile.editText!!.focusChanges()

    init {
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_item,
                view.resources.getStringArray(com.ocs.sequre.R.array.country_code_name_array)
            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.input_country.adapter = dataAdapter
        view.input_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.findViewById<TextView>(android.R.id.text1)?.text =
                    view?.resources!!.getStringArray(com.ocs.sequre.R.array.country_code_array)[position]
            }
        }

//        Observable.combineLatest(
//            email,
//            emailFocusChanges,
//            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
//        ).distinctUntilChanged()
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(::println, Throwable::printStackTrace)
//
//        Observable.combineLatest(
//            phone,
//            phoneFocusChanges,
//            BiFunction { t1: Boolean, t2: Boolean -> t1 && t2 }
//        ).distinctUntilChanged()
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(::println, Throwable::printStackTrace)
    }

    fun set(obj: Registration) {
        view.input_country.setSelection(0, true)
        view.input_mobile.editText?.setText(obj.mobile)
        view.input_name.editText?.setText(obj.name)
        view.input_email.editText?.setText(obj.email)
        view.input_password.editText?.setText(obj.password)
        view.input_password_confirm.editText?.setText(obj.password)
    }

    fun get(): Registration {
        return Registration(
            name = view.input_name.text().toString(),
            email = view.input_email.text().toString(),
            countryCode = view.resources.getStringArray(com.ocs.sequre.R.array.country_code_array)[view.input_country.selectedItemPosition],
            mobile = view.input_mobile.text().toString(),
            password = view.input_password.text().toString()
        )
    }

    fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
            name,
            email,
            phone,
            password,
            passwordConfirm,
            Function5 { name: Boolean, email: Boolean, mobile: Boolean, password: Boolean, passwordConfirm: Boolean ->
                name && email && mobile && password && passwordConfirm
            }
        ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }
}