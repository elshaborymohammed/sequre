package com.ocs.sequre.app.base

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.compact.app.CompactFragment
import com.compact.response.ApiException
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
        progressBar = AlertDialog.Builder(requireContext())
            .setView(R.layout.layout_progress_bar)
            .setCancelable(false)
            .create()
        progressBar.window?.run {
            setBackgroundDrawableResource(android.R.color.transparent)
            allowEnterTransitionOverlap = true
        }
    }

    fun exitProcess() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }
            })
    }

    protected fun loading(it: Boolean) {
        if (it) progressBar.show() else progressBar.dismiss()
    }

    protected fun setToolBar(toolbar: Toolbar) {
//        (activity as AppCompatActivity)?.apply {
//            setSupportActionBar(toolbar)
//        }
    }

    protected fun onSuccess() =
        Snackbar.make(
            requireView(),
            getString(R.string.saved_successfully),
            Snackbar.LENGTH_LONG
        ).show()

    protected open fun onError(): (it: Throwable) -> Unit {
        return {
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

    open fun onApiException(code: ErrorStatus, errors: List<Error>) {
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

    open fun onServerError() {
        Snackbar.make(
            requireView(),
            "Internal Server Error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    open fun onIOException() {
        Snackbar.make(
            requireView(),
            "Internet connection lost",
            Snackbar.LENGTH_LONG
        ).show()
    }

    open fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}