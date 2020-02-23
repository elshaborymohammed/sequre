package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.*
import com.compact.app.extensions.email
import com.compact.app.extensions.notNullOrEmpty
import com.compact.app.extensions.phone
import com.compact.app.extensions.username
import com.jakewharton.rxbinding3.view.focusChanges
import com.ocs.sequre.app.CompactDatePicker
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

abstract class UserDataViewHolder constructor(private val view: View) {
    public val relationship: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty()
    protected val name: Observable<Boolean>
        get() = view.input_name.username()
    protected val phone: Observable<Boolean>
        get() = view.input_phone.phone()
    protected val email: Observable<Boolean>
        get() = view.input_email.email()
    protected val gender: Observable<Boolean>
        get() = view.input_gender.notNullOrEmpty()
    protected val birthDate: Observable<Boolean>
        get() = view.input_birth_date.notNullOrEmpty()

    private val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    private val phoneFocusChanges: Observable<Boolean>
        get() = view.input_phone.editText!!.focusChanges()

    init {
        view.input_country.apply {
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_item,
                    view.resources.getStringArray(com.ocs.sequre.R.array.country_code_name_array)
                )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        view.input_gender.apply {
            (editText as AutoCompleteTextView).run {
                threshold = 1 //will start working from first character
                setAdapter(
                    ArrayAdapter(
                        view.context,
                        android.R.layout.select_dialog_item,
                        view.resources.getStringArray(com.ocs.sequre.R.array.gender_array)
                    )
                )
            }
        }

        view.input_birth_date.apply {
            setOnClickListener {
                CompactDatePicker.builder(it.context)
                    .maxDate(System.currentTimeMillis())
                    .onDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        view.input_birth_date.editText!!.setText(String.format("$year/${month + 1}/$dayOfMonth"))
                    }.build()
            }
            setEndIconOnClickListener { performClick() }
        }
    }

    abstract fun validations(): Observable<Boolean>
}