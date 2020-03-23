package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.compact.picker.ImagePicker
import com.compact.response.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.app.base.toBase64
import com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.data.remote.model.response.error.ResponseError
import com.ocs.sequre.domain.entity.Document
import com.ocs.sequre.presentation.viewmodel.MedicalDocumentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_medical_document_edit.view.*
import java.io.IOException

class MedicalDocumentEditFragment : BaseBottomSheet() {

    private lateinit var viewModel: MedicalDocumentViewModel
    private lateinit var obj: Document

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_medical_document_edit
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProvider(this, factory).get(MedicalDocumentViewModel::class.java)

        arguments?.apply {
            MedicalDocumentEditFragmentArgs.fromBundle(this).document.apply {
                obj = this
                GlideApp.with(this@MedicalDocumentEditFragment)
                    .load(photo)
                    .optionalFitCenter()
                    .into(view.document)

                view.update.setOnClickListener {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            ImagePicker.PERMISSIONS[0]
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            ImagePicker.PERMISSIONS,
                            ImagePicker.REQUEST_CODE
                        )
                    } else {
                        ImagePicker.build(this@MedicalDocumentEditFragment)
                    }
                }
                view.delete.setOnClickListener {
                    subscribe(
                        viewModel.delete(id, category).subscribe(onSuccess(), onError())
                    )
                }
            }
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ImagePicker.REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                        GlideApp.with(this).asBitmap().load(data.extras!!["data"] as Bitmap)
                            .into(uploadImage())
                    }
                    null != data.data -> {
                        GlideApp.with(this).asBitmap().load(data.data).into(uploadImage())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onSuccess(): () -> Unit {
        return {
            Toast.makeText(
                requireContext(),
                getString(R.string.saved_successfully),
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigateUp()
        }
    }

    private fun onError(): (it: Throwable) -> Unit {
        return {
            it.printStackTrace()
            try {
                if (it is ApiException) {
                    if (it.code() >= 500) {
//                        onServerError()
                    } else {
                        it.error(ResponseError::class.java)?.run {
                            onApiException(code, errors)
                        }
                    }
                } else if (it is IOException) {
                    onIOException()
                } else {
                    onError(it.message!!)
                }
            } catch (e: Exception) {
                onIOException()
            }
        }
    }

    private fun onApiException(code: ErrorStatus, errors: List<Error>) {
        if (code == ErrorStatus.VALIDATION) {
            for (e in errors) {
                requireView().findViewWithTag<TextInputLayout>(e.path)
                    ?.apply {
                        error = e.message
                        setEndIconOnClickListener { performClick() }
                    }
            }
        } else if (errors.isNotEmpty() && code != ErrorStatus.VALIDATION) {
            Toast.makeText(context, errors[0].message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onServerError() {
        Snackbar.make(
            requireView(),
            "Internal Server Error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun onIOException() {
        Snackbar.make(
            requireView(),
            "Internet connection lost",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun uploadImage(): CustomTarget<Bitmap> {
        return object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                subscribe(
                    viewModel.delete(obj.id, obj.category).subscribe({
                        subscribe(
                            viewModel.post(
                                MedicalDocumentBody(
                                    category = obj.category,
                                    photo = resource.toBase64()
                                )
                            ).subscribe({
                                Snackbar.make(
                                    requireView(),
                                    R.string.saved_successfully,
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }, onError())
                        )
                    }, onError())
                )
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        }
    }
}