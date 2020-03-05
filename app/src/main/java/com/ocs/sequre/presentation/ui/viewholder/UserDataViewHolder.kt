package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.*
import com.compact.app.extensions.email
import com.compact.app.extensions.fullName
import com.compact.app.extensions.notNullOrEmpty
import com.compact.app.extensions.phone
import com.jakewharton.rxbinding3.view.focusChanges
import com.ocs.sequre.R
import com.ocs.sequre.app.CompactDatePicker
import com.ocs.sequre.app.base.setAdapter
import com.ocs.sequre.domain.entity.Country
import com.ocs.sequre.domain.entity.Relationship
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

abstract class UserDataViewHolder constructor(
    private val view: View,
    private val textChangesSkip: Long = 1,
    private val focusChangesSkip: Long = 1
) {
    protected val name: Observable<Boolean>
        get() = view.input_name.fullName(textChangesSkip, focusChangesSkip)
    protected val phone: Observable<Boolean>
        get() = view.input_phone.phone(textChangesSkip, focusChangesSkip)
    protected val email: Observable<Boolean>
        get() = view.input_email.email(textChangesSkip, focusChangesSkip)
    protected val relationship: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty(textChangesSkip, focusChangesSkip)
    protected val gender: Observable<Boolean>
        get() = view.input_gender.notNullOrEmpty(textChangesSkip, focusChangesSkip)
    protected val birthDate: Observable<Boolean>
        get() = view.input_birth_date.notNullOrEmpty(textChangesSkip, focusChangesSkip)

    private val emailFocusChanges: Observable<Boolean>
        get() = view.input_email.editText!!.focusChanges()
    private val phoneFocusChanges: Observable<Boolean>
        get() = view.input_phone.editText!!.focusChanges()

    init {
        view.input_relationship.apply {
            (editText as AutoCompleteTextView).run {
                ArrayAdapter(
                    view.context,
                    R.layout.select_dialog_item,
                    R.id.text,
                    Relationship.values()
                ).also {
                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            view?.findViewById<TextView>(R.id.text)?.apply {
                                setText(it.getItem(position)!!.stringRes)
                            }
                        }
                    }
                }
            }

        }

        view.input_relationship.setAdapter(Relationship.values(view.context))
        view.input_gender.setAdapter(R.array.gender_array)

//        view.input_relationship.apply {
//            (editText as AutoCompleteTextView).run {
//                threshold = 1 //will start working from first character
//                setAdapter(
//                    ArrayAdapter(
//                        context,
//                        com.ocs.sequre.R.layout.select_dialog_item,
//                        com.ocs.sequre.R.id.text,
//                        view.resources.getStringArray(com.ocs.sequre.R.array.relationship_array)
//                    )
//                )
//            }
//        }

//        view.input_gender.apply {
//            (editText as AutoCompleteTextView).run {
//                threshold = 1 //will start working from first character
//                setAdapter(
//                    ArrayAdapter(
//                        context,
//                        com.ocs.sequre.R.layout.select_dialog_item,
//                        com.ocs.sequre.R.id.text,
//                        view.resources.getStringArray(com.ocs.sequre.R.array.gender_array)
//                    )
//                )
//            }
//        }

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
        view.input_country.apply {
            (editText as AutoCompleteTextView).run {
                threshold = 1 //will start working from first character
                ArrayAdapter(
                    view.context,
                    R.layout.select_dialog_item,
                    R.id.text,
                    it
                ).run {
//                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                        override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                        }
//
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View?,
//                            position: Int,
//                            id: Long
//                        ) {
//                            view?.findViewById<TextView>(android.R.id.text1)?.apply {
//                                text = it[position].code
//                            }
//                        }
//                    }
                    setAdapter(this)
                }
                setText("+20", false)
            }
        }
    }

    abstract fun validations(): Observable<Boolean>
}