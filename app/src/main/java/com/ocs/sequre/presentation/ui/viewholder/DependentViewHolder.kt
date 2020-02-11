package com.ocs.sequre.presentation.ui.viewholder

import android.app.DatePickerDialog
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleObserver
import com.compact.app.extensions.email
import com.compact.app.extensions.notNullOrEmpty
import com.compact.app.extensions.phone
import com.compact.app.extensions.text
import com.compact.helper.ImageHelper
import com.ocs.sequre.domain.entity.Dependent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

@RequiresApi(Build.VERSION_CODES.N)
class DependentViewHolder constructor(private val view: View) : LifecycleObserver {
    private val relation: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty()
    private val name: Observable<Boolean>
        get() = view.input_name.notNullOrEmpty()
    private val email: Observable<Boolean>
        get() = view.input_email.email()
    private val phone: Observable<Boolean>
        get() = view.input_phone.phone()
    private val birthDate: Observable<Boolean>
        get() = view.input_birth_date.notNullOrEmpty()

    private val photo: String
        get() = ImageHelper.encodeBitmapToBase64(view.input_avatar.drawable.toBitmap())

    private val id: Int = -1

    init {
        view.input_country.run {
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_item,
                    view.resources.getStringArray(com.ocs.sequre.R.array.country_code_name_array)
                )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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

        (view.input_gender.editText as AutoCompleteTextView)
            .run {
                threshold = 1 //will start working from first character
                setAdapter(
                    ArrayAdapter(
                        view.context,
                        android.R.layout.select_dialog_item,
                        view.resources.getStringArray(com.ocs.sequre.R.array.gender_array)
                    )
                )
            }

        view.input_birth_date.setEndIconOnClickListener {
            val picker = DatePickerDialog(it.context)
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.setOnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                view.input_birth_date.editText!!.setText(String.format("$year/$month/$dayOfMonth"))
            }
            picker.show()
        }
    }

    fun set(obj: Dependent) {
        view.apply {
            id = obj.id
            input_country.setSelection(0, true)
            input_phone.editText?.setText(obj.phone)
            input_name.editText?.setText(obj.name)
            input_email.editText?.setText(obj.email)
            input_birth_date.editText?.setText((obj.birthDate ?: "").toString())
            (input_gender.editText as AutoCompleteTextView).apply {
                setText(obj.gender, false)
            }
        }
    }

    fun get(): Dependent {
        return Dependent(
            id = id,
            relationship = view.input_relationship.text().toString(),
            name = view.input_name.text().toString(),
            email = view.input_email.text().toString(),
            countryCode = view.resources.getStringArray(com.ocs.sequre.R.array.country_code_array)[view.input_country.selectedItemPosition],
            phone = view.input_phone.text().toString(),
            gender = view.input_gender.text().toString(),
            birthDate = view.input_birth_date.text().toString()
        )
    }
}