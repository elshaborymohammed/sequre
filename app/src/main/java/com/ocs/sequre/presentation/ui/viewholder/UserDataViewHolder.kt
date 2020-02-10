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
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_user_create_account.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*

class UserDataViewHolder constructor(private val view: View) {
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
    }

    fun set(obj: Registration) {
        view.input_country.setSelection(0, true)
        view.input_phone.editText?.setText(obj.phone)
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
            phone = view.input_phone.text().toString(),
            password = view.input_password.text().toString()
        )
    }

    protected val name: Observable<Boolean>
        get() = view.input_name.notNullOrEmpty()
    protected val email: Observable<Boolean>
        get() = view.input_email.email()
    protected val phone: Observable<Boolean>
        get() = view.input_phone.phone()

    protected val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    protected val phoneFocusChanges: Observable<Boolean>
        get() = view.input_phone.editText!!.focusChanges()

    fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
            name,
            email,
            phone,
            Function3 { name: Boolean, email: Boolean, phone: Boolean ->
                name && email && phone
            }
        ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }

    protected fun emailValidation(block: ((email: String) -> Unit)): Observable<Boolean> =
        Observable.combineLatest(
            email,
            emailFocusChanges,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && !t2 })
            .distinctUntilChanged()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it) block(view.input_email.text().toString()) }

    protected fun phoneValidation(block: ((phone: String) -> Unit)): Observable<Boolean> =
        Observable.combineLatest(
            phone,
            phoneFocusChanges,
            BiFunction { t1: Boolean, t2: Boolean -> t1 && !t2 }
        ).distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { if (it) block(view.input_phone.text().toString()) }
}