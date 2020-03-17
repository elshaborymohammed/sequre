package com.ocs.sequre.presentation.ui.fragment.dependent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.compact.picker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.presentation.ui.viewholder.DependentViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import com.ocs.sequre.presentation.viewmodel.DependentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_data.*
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_user_main_data.view.*

abstract class DependentFragment(
    private val textChangesSkip: Long,
    private val focusChangesSkip: Long
) : BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    protected lateinit var dependentViewModel: DependentViewModel
    protected lateinit var viewHolder: DependentViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_data
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder =
            DependentViewHolder(
                view,
                textChangesSkip = textChangesSkip,
                focusChangesSkip = focusChangesSkip
            )

        authViewModel =
            ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        dependentViewModel =
            ViewModelProvider(this, factory).get(DependentViewModel::class.java)

        view.input_avatar.setOnClickListener {
            requestImageCapture()
        }
        view.camera.setOnClickListener {
            view.input_avatar.performClick()
        }

        view.update.setOnClickListener {
            onSaveClicked(viewHolder.get())
        }
        view.delete.setOnClickListener {
            subscribe(
                dependentViewModel.delete(viewHolder.get().id).subscribe(::onSuccess, onError())
            )
        }

        view.update.isEnabled = false
        onDataLoaded()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ImagePicker.REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    ImagePicker.build(this)
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                when {
                    data!!.hasExtra("data") -> {
                        GlideApp.with(this).load(data.extras!!["data"] as Bitmap).into(input_avatar)
                    }
                    null != data.data -> {
                        GlideApp.with(this).load(data.data).into(input_avatar)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewHolder.validations().subscribe(
                requireView().update::setEnabled,
                Throwable::printStackTrace
            ),
            dependentViewModel.loading().subscribe(::loading),
            authViewModel.loading().subscribe(::loading),
            countries()
        )
    }

    private fun countries(): Disposable = authViewModel.countryCode()
        .subscribe({
            viewHolder.setCountries(it)
            view!!.input_country.apply {
                endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                setEndIconDrawable(R.drawable.ic_chevron_down)
            }
        }, {
            onError()
            view!!.input_country.apply {
                setEndIconDrawable(R.drawable.ic_chevron_down)
                endIconMode = TextInputLayout.END_ICON_CUSTOM
                setEndIconOnClickListener {
                    subscribe(countries())
                }
            }
        })

    open fun onDataLoaded() {}

    abstract fun onSaveClicked(it: Dependent)
}