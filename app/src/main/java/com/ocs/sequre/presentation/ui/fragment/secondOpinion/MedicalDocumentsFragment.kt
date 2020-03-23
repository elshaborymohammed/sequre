package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.compact.picker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.app.base.toBase64
import com.ocs.sequre.data.remote.model.request.secondopinion.DocumentType
import com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody
import com.ocs.sequre.domain.entity.Document
import com.ocs.sequre.presentation.ui.adapter.MedicalDocumentAdapter
import com.ocs.sequre.presentation.viewmodel.MedicalDocumentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_medical_documents.view.*

class MedicalDocumentsFragment : BaseFragment() {

    private lateinit var viewModel: MedicalDocumentViewModel
    private lateinit var medicalReportsAdapter: MedicalDocumentAdapter
    private lateinit var labTestsAdapter: MedicalDocumentAdapter
    private lateinit var radiologyScansAdapter: MedicalDocumentAdapter

    @DocumentType
    private var category: Int = MedicalDocumentBody.LAB

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_medical_documents
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(MedicalDocumentViewModel::class.java)

        MedicalDocumentAdapter().run {
            setOnItemClickListener(
                { onNewListener(MedicalDocumentBody.MEDICAL) },
                ::onChangeListener
            )
            medicalReportsAdapter = this
            view.medical_reports.adapter = this
        }

        MedicalDocumentAdapter().run {
            setOnItemClickListener({ onNewListener(MedicalDocumentBody.LAB) }, ::onChangeListener)
            labTestsAdapter = this
            view.lab_tests.adapter = this
        }

        MedicalDocumentAdapter().run {
            setOnItemClickListener(
                { onNewListener(MedicalDocumentBody.RADIOLOGY) },
                ::onChangeListener
            )
            radiologyScansAdapter = this
            view.radiology_scans.adapter = this
        }

        view.choose_doctor.setOnClickListener {
            findNavController().navigate(R.id.action_medicalDocumentsFragment_to_secondOpinionDoctorsFragment)
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.get(1).subscribe({
                medicalReportsAdapter.swap(it.medical)
                labTestsAdapter.swap(it.lab)
                radiologyScansAdapter.swap(it.radiology)
            }, onError())
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
                        GlideApp.with(this)
                            .asBitmap()
                            .load(data.extras!!["data"] as Bitmap)
                            .into(uploadImage())
                    }
                    null != data.data -> {
                        GlideApp.with(this)
                            .asBitmap()
                            .load(data.data)
                            .into(uploadImage())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onNewListener(@DocumentType category: Int) {
        this.category = category
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
            ImagePicker.build(this)
        }
    }

    private fun onChangeListener(it: Document) {
        findNavController().navigate(
            R.id.action_medicalDocumentsFragment_to_medicalDocumentEditFragment,
            MedicalDocumentEditFragmentArgs(it).toBundle()
        )
    }

    private fun uploadImage(): CustomTarget<Bitmap> {
        return object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                subscribe(
                    viewModel.post(
                        MedicalDocumentBody(
                            category = category,
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