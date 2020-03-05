package com.ocs.sequre.app.base

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.compact.app.CompactFragment
import com.compact.picker.ImagePicker
import com.compact.response.ApiException
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.data.remote.model.response.error.ResponseError
import java.io.IOException
import javax.inject.Inject
import kotlin.system.exitProcess


abstract class BaseFragment : CompactFragment() {
    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

    private lateinit var progressBar: AlertDialog

    override fun onViewBound(view: View) {
        hideKeyboard(requireActivity())
        view.findViewById<MaterialToolbar>(R.id.toolbar)?.apply {
            setNavigationOnClickListener {
                Navigation.findNavController(view).navigateUp()
            }
//            setToolBar(this)
        }
        progressBar = AlertDialog.Builder(requireContext())
            .setView(R.layout.layout_progress_bar)
            .setCancelable(false)
            .create()
        progressBar.window?.run {
            setBackgroundDrawableResource(android.R.color.transparent)
            allowEnterTransitionOverlap = true
        }
    }

    open fun hideKeyboard(activity: Activity) {
        val inputManager: InputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun exitProcess() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        exitProcess(0)
                    }
                })
    }

    protected fun loading(it: Boolean) {
        if (it) progressBar.show() else progressBar.dismiss()
    }

    protected fun setToolBar(toolbar: Toolbar) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
        }
    }

    protected fun onSuccess() {
        Toast.makeText(
            requireContext(),
            getString(R.string.saved_successfully),
            Toast.LENGTH_SHORT
        ).show()

        findNavController().navigateUp()
    }

    protected open fun onError(): (it: Throwable) -> Unit {
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

    protected open fun onApiException(code: ErrorStatus, errors: List<Error>) {
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

    protected open fun onServerError() {
        Snackbar.make(
            requireView(),
            "Internal Server Error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected open fun onIOException() {
        Snackbar.make(
            requireView(),
            "Internet connection lost",
            Snackbar.LENGTH_LONG
        ).show()
    }

    open fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected fun requestImageCapture() {
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
}