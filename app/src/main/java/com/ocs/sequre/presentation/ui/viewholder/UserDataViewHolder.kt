package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.*
import com.compact.app.extensions.*
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.textChanges
import com.ocs.sequre.app.CompactDatePicker
import com.ocs.sequre.domain.entity.Country
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

abstract class UserDataViewHolder constructor(private val view: View, private val skip: Long = 1) {
    protected val relationship: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty(skip)
    protected val name: Observable<Boolean>
        get() = view.input_name.username(skip)
    protected val phone: Observable<Boolean>
        get() = view.input_phone.phone(skip)
    protected val email: Observable<Boolean>
        get() = view.input_email.email(skip)
    protected val gender: Observable<Boolean>
        get() = view.input_gender.editText!!.textChanges().map { it.isNotNullOrEmpty() }
    protected val birthDate: Observable<Boolean>
        get() = view.input_birth_date.editText!!.textChanges().map { it.isNotNullOrEmpty() }

    private val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    private val phoneFocusChanges: Observable<Boolean>
        get() = view.input_phone.editText!!.focusChanges()

    private lateinit var countries: List<Country>

    init {
        view.input_gender.apply {
            (editText as AutoCompleteTextView).run {
                threshold = 1 //will start working from first character
                setAdapter(
                    ArrayAdapter(
                        view.context,
                        com.ocs.sequre.R.layout.select_dialog_item,
                        com.ocs.sequre.R.id.text,
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

    fun setCountries(it: List<Country>) {
        countries = it
        view.input_country.apply {
            val dataAdapter: ArrayAdapter<Country> =
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_item,
                    countries
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
                    view?.findViewById<TextView>(android.R.id.text1)?.text = it[position].code
                }
            }
        }
    }

    fun selectCountry(code: String) {
        view.input_country.apply {
            adapter?.let {
                if (countries.isNotEmpty()) {
                    setSelection(
                        countries.indexOfFirst {
                            it.code == code
                        }, true
                    )
                }
            }
        }
    }

    abstract fun validations(): Observable<Boolean>
}