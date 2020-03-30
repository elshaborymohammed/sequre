package com.ocs.sequre.presentation.ui.fragment.secondOpinion

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
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.app.base.toBase64
import com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody
import com.ocs.sequre.domain.entity.Document
import com.ocs.sequre.presentation.viewmodel.MedicalDocumentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_medical_document_edit.view.*

class MedicalDocumentEditFragment : BaseBottomSheet() {

    private lateinit var viewModel: MedicalDocumentViewModel
    private lateinit var obj: Document
    private var categoryId: Int = 0

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_medical_document_edit
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel =
            ViewModelProvider(activity!!, factory).get(MedicalDocumentViewModel::class.java)

        arguments?.apply {
            MedicalDocumentEditFragmentArgs.fromBundle(this).apply {
                obj = document
                categoryId = category
                GlideApp.with(this@MedicalDocumentEditFragment)
                    .load(document.photo)
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
                        viewModel.delete(document.id, category).subscribe({
                            viewModel.reload.accept("")
                            findNavController().navigateUp()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.saved_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onError())
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

    private fun uploadImage(): CustomTarget<Bitmap> {
        return object : CustomTarget<Bitmap>() {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                subscribe(
                    viewModel.delete(obj.id, categoryId).subscribe({
                        subscribe(
                            viewModel.post(
                                MedicalDocumentBody(
                                    category = categoryId,
                                    photo = bitmap.toBase64()
                                )
                            ).subscribe({
                                view?.document?.setImageBitmap(bitmap)
                                viewModel.reload.accept("")
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