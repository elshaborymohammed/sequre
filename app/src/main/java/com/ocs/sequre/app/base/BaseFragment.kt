package com.ocs.sequre.app.base

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import com.compact.app.CompactFragment
import com.ocs.sequre.R
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.data.remote.model.response.error.ResponseError
import com.smart.compact.response.ApiException
import kotlinx.android.synthetic.main.activity_main.*
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
            setBackgroundDrawableResource(android.R.color.transparent);
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

    protected fun loadingOn() {
        progressBar.show()
    }

    protected fun loadingOff() {
        progressBar.dismiss()
    }

    protected fun setToolBar(toolbar: Toolbar) {
//        (activity as AppCompatActivity)?.apply {
//            setSupportActionBar(toolbar)
//        }
    }

    protected open fun onError(): (it: Throwable) -> Unit {
        return {
            if (it is ApiException) {
                if (it.code() >= 500) {
                    onServerError()
                } else {
                    it.error(ResponseError::class.java)?.run {
                        onApiException(code, errors)
                    }
                }
            } else if (it is IOException) {
                onIOException()
            } else {
                onError(it.message)
            }
        }
    }

    open fun onApiException(code: ErrorStatus, errors: List<Error>) {
        if (errors.isNotEmpty()) {
            Toast.makeText(context, errors[0].message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Bad Request", Toast.LENGTH_LONG).show()
        }
    }

    open fun onServerError() {
        Toast.makeText(context, "Internal Server Error", Toast.LENGTH_LONG).show()
    }

    open fun onIOException() {
        Toast.makeText(context, "Connection Lost", Toast.LENGTH_LONG).show()
    }

    open fun onError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}