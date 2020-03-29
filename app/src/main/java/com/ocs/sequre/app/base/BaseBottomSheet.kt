package com.ocs.sequre.app.base

import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.app.CompactBottomSheetFragment
import com.compact.response.ApiException
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.data.remote.model.response.error.ResponseError
import java.io.IOException
import javax.inject.Inject

abstract class BaseBottomSheet : CompactBottomSheetFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var progressBar: AlertDialog

    //    @NonNull
    //    @Override
    //    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    //        Dialog dialog = super.onCreateDialog(savedInstanceState);
    //        if (dialog.getWindow() != null) {
    //            System.out.println("onCreateDialog");
    ////            dialog.getWindow().setGravity(Gravity.BOTTOM);
    ////            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    ////            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    //            //dialog.setCancelable(false);
    //        }
    //        return dialog;
    //    }

    @CallSuper
    override fun onViewBound(view: View) {
        AlertDialog.Builder(view.context).apply {
            setView(R.layout.layout_progress_bar)
            setCancelable(false)
            create().run {
                window?.run {
                    setBackgroundDrawableResource(android.R.color.transparent)
                    allowEnterTransitionOverlap = true
                }
                progressBar = this
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
    }

    protected fun loading(it: Boolean) {
        if (it) progressBar.show() else progressBar.dismiss()
    }

    protected fun onSuccess(): () -> Unit {
        return {
            Toast.makeText(
                requireContext(),
                getString(R.string.saved_successfully),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }
    }

    protected fun onError(): (it: Throwable) -> Unit {
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
}