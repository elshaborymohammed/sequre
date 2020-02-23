package com.ocs.sequre.presentation.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.compact.picker.ImagePicker
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.presentation.ui.viewholder.DependentViewHolder
import com.ocs.sequre.presentation.viewmodel.DependentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_data.*
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

abstract class DependentFragment : BaseFragment() {

    protected lateinit var viewModel: DependentViewModel
    protected lateinit var viewHolder: DependentViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_data
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder = DependentViewHolder(view)

        viewModel =
            ViewModelProvider(this, factory).get(DependentViewModel::class.java)

        view.input_avatar.setOnClickListener {
            ImagePicker.build(this)
        }
        view.update.setOnClickListener {
            onSaveClicked(viewHolder.get())
        }
        view.delete.setOnClickListener {
            subscribe(
                viewModel.delete(viewHolder.get().id).subscribe(::onSuccess, onError())
            )
        }
        view.input_avatar.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    ImagePicker.PERMISSIONS[0]
                ) != PERMISSION_GRANTED
            ) {
                requestPermissions(
                    ImagePicker.PERMISSIONS,
                    ImagePicker.REQUEST_CODE
                )
            } else {
                ImagePicker.build(this)
            }
        }

        view.update.isEnabled = false
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
            viewModel.loading().subscribe(::loading),
            viewHolder.validations().subscribe(
                ::println,
                Throwable::printStackTrace
            )
        )
    }

    abstract fun onSaveClicked(it: Dependent)
}