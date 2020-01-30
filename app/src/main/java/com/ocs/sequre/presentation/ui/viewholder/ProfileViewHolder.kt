package com.ocs.sequre.presentation.ui.viewholder

import android.app.DatePickerDialog
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import com.compact.app.extensions.email
import com.compact.app.extensions.notNullOrEmpty
import com.compact.app.extensions.phone
import com.ocs.sequre.domain.entity.Profile
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_user_create_account.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class ProfileViewHolder constructor(private val view: View) : LifecycleObserver {
    private val name: Observable<Boolean>
        get() = view.input_mobile.phone()
    private val email: Observable<Boolean>
        get() = view.input_email.email()
    private val phone: Observable<Boolean>
        get() = view.input_mobile.phone()
    private val relation: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty()
    private val birthDate: Observable<Boolean>
        get() = view.input_birth_date.notNullOrEmpty()

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
            picker.show()
        }
    }

    fun set(obj: Profile) {
        view.input_country.setSelection(0, true)
        view.input_mobile.editText?.setText(obj.mobile)
        view.input_name.editText?.setText(obj.name)
        view.input_email.editText?.setText(obj.email)
        view.input_relationship.editText?.setText(obj.relation)
        view.input_birth_date.editText?.setText(obj.birthDate.toString())
    }

    fun get(): Profile {
        return Profile(
            name = view.input_name.editText?.text.toString(),
            email = view.input_email.editText?.text.toString(),
            countryCode = view.resources.getStringArray(com.ocs.sequre.R.array.country_code_array)[view.input_country.selectedItemPosition],
            mobile = view.input_mobile.editText?.text.toString(),
            relation = view.input_relationship.editText?.text.toString(),
            birthDate = Date()
        )
    }

    fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
            name,
            email,
            phone,
            relation,
            birthDate,
            Function5 { name: Boolean, email: Boolean, mobile: Boolean, relation: Boolean, birthDate: Boolean ->
                name && email && mobile && relation && birthDate
            }
        ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun validations2(): Observable<Boolean> {
        return Observable.combineLatest(
            Function<Array<Any>, Boolean> {
                it[0] as Boolean && it[1] as Boolean && it[2] as Boolean
            },
            Flowable.bufferSize(),
            name,
            email,
            phone,
            relation,
            birthDate
        ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }
}