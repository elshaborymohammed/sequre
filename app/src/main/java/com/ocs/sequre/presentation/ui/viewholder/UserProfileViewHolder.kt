package com.ocs.sequre.presentation.ui.viewholder

import android.view.View
import android.widget.AutoCompleteTextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.compact.app.extensions.text
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.base64
import com.ocs.sequre.domain.entity.Profile
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function5
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*
import kotlinx.android.synthetic.main.layout_user_profile_data.view.*

class UserProfileViewHolder constructor(private val view: View) : UserDataViewHolder(view, 1, 0) {

    init {
        view.input_relationship.visibility = View.GONE
    }

    override fun validations(): Observable<Boolean> {
        return Observable.combineLatest(
            name,
            email,
            phone,
            gender,
            birthDate,
            Function5 { name: Boolean, email: Boolean, phone: Boolean, gender: Boolean, birthDate: Boolean ->
                name && email && phone && gender && birthDate
            }
        ).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun set(obj: Profile) {
        view.apply {
            input_phone.text(obj.phone ?: "")
            input_name.text(obj.name ?: "")
            input_email.text(obj.email ?: "")
            input_birth_date.text(obj.birthDate ?: "")
            (input_gender.editText as AutoCompleteTextView).apply {
                setText(obj.gender, false)
            }
            (input_country.editText as AutoCompleteTextView).apply {
                setText(obj.countryCode ?: "+20", false)
            }
            GlideApp.with(input_avatar)
                .load(obj.photo)
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .signature(ObjectKey(obj.photo ?: ""))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(input_avatar)
        }
    }

    fun get(): Profile {
        return Profile(
            name = view.input_name.text(),
            email = view.input_email.text(),
            countryCode = view.input_country.text(),
            phone = view.input_phone.text(),
            gender = view.input_gender.text(),
            birthDate = view.input_birth_date.text(),
            photo = view.input_avatar.base64()
        )
    }
}