package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleObserver
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.app.extensions.email
import com.compact.app.extensions.notNullOrEmpty
import com.compact.app.extensions.phone
import com.compact.app.extensions.text
import com.ocs.sequre.R
import com.ocs.sequre.app.CompactDatePicker
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.base64
import com.ocs.sequre.domain.entity.Dependent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function6
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

class DependentViewHolder constructor(private val view: View) : LifecycleObserver {
    private val relationship: Observable<Boolean>
        get() = view.input_relationship.notNullOrEmpty()
    private val name: Observable<Boolean>
        get() = view.input_name.notNullOrEmpty()
    private val email: Observable<Boolean>
        get() = view.input_email.email()
    private val phone: Observable<Boolean>
        get() = view.input_phone.phone()
    private val gender: Observable<Boolean>
        get() = view.input_gender.notNullOrEmpty()
    private val birthDate: Observable<Boolean>
        get() = view.input_birth_date.notNullOrEmpty()

    private var id: Int = -1

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

        view.input_birth_date.apply {
            setOnClickListener {
                CompactDatePicker.builder(it.context)
                    .maxDate(System.currentTimeMillis())
                    .onDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        view.input_birth_date.editText!!.setText(String.format("$year/$month/$dayOfMonth"))
                    }.build()
            }
            setEndIconOnClickListener { performClick() }
        }
    }

    fun set(obj: Dependent) {
        view.apply {
            this@DependentViewHolder.id = obj.id
            input_country.setSelection(0, true)
            input_relationship.editText?.setText(obj.relationship)
            input_phone.editText?.setText(obj.phone)
            input_name.editText?.setText(obj.name)
            input_email.editText?.setText(obj.email)
            input_birth_date.editText?.setText(obj.birthDate)
            (input_gender.editText as AutoCompleteTextView).apply {
                setText(obj.gender, false)
            }
            GlideApp.with(input_avatar)
                .load(obj.photo)
                .error(R.drawable.ic_profile_avatar)
                .signature(ObjectKey(obj.photo!!))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(input_avatar)
        }
    }

    fun get(): Dependent {
        return Dependent(
            id = id,
            relationship = view.input_relationship.text(),
            name = view.input_name.text(),
            email = view.input_email.text(),
            countryCode = view.resources.getStringArray(com.ocs.sequre.R.array.country_code_array)[view.input_country.selectedItemPosition],
            phone = view.input_phone.text(),
            gender = view.input_gender.text(),
            birthDate = view.input_birth_date.text(),
            photo = view.input_avatar.base64()
        )
    }

    fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
            relationship,
            name,
            email,
            phone,
            gender,
            birthDate,
            Function6 { relation: Boolean, name: Boolean, email: Boolean, phone: Boolean, gender: Boolean, birthDate: Boolean ->
                relation && name && email && phone && gender && birthDate
            }
        ).distinctUntilChanged()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }
}