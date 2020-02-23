package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.AutoCompleteTextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.app.extensions.text
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.base64
import com.ocs.sequre.domain.entity.Dependent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function6
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

class DependentViewHolder constructor(private val view: View) : UserDataViewHolder(view) {
    private var id: Int = -1

    override fun validations(): Observable<Boolean> {
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
            .observeOn(AndroidSchedulers.mainThread())
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
            countryCode = view.resources.getStringArray(R.array.country_code_array)[view.input_country.selectedItemPosition],
            phone = view.input_phone.text(),
            gender = view.input_gender.text(),
            birthDate = view.input_birth_date.text(),
            photo = view.input_avatar.base64()
        )
    }
}