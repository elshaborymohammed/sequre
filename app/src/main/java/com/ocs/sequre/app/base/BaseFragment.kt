package com.ocs.sequre.app.base

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.compact.app.CompactFragment
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ResponseErrors
import com.smart.compact.response.ApiException
import io.reactivex.functions.Consumer
import java.io.IOException
import javax.inject.Inject
import kotlin.system.exitProcess

abstract class BaseFragment : CompactFragment() {
    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

//    @Inject
//    protected lateinit var auth: AuthenticationPreference

    override fun onViewBound(view: View) {

    }

    fun exitProcess() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }

            })
    }

    protected fun setToolBar(toolbar: Toolbar) {
//        (activity as AppCompatActivity)?.apply {
//            setSupportActionBar(toolbar)
//        }
    }

    protected open fun onError(): Consumer<Throwable> {
        return Consumer {
            if (it is ApiException) {
                if (it.code() >= 500) {
                    onServerError()
                } else {
                    onApiException(it.code(), it.error(ResponseErrors::class.java)!!.errors)
                }
            } else if (it is IOException) {
                onIOException()
            }
        }
    }

    open fun onApiException(code: Int, errors: List<Error>) {
        Toast.makeText(context, "Bad Request", Toast.LENGTH_LONG).show()
    }

    open fun onServerError() {
        Toast.makeText(context, "Internal Server Error", Toast.LENGTH_LONG).show()
    }

    open fun onIOException() {
        Toast.makeText(context, "Connection Lost", Toast.LENGTH_LONG).show()
    }

//    open fun onError(): Consumer<in Throwable> {
//        return Consumer {
//            it.printStackTrace()
//            if (it is ApiException) {
//                when (it.code()) {
//                    401 -> if (auth.clear()) {
//                        val intent = Intent(activity, LandingActivity::class.java)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//                    }
//                    else -> {
//                        it.error(ApiError::class.java)?.errors?.messages?.get(0)
//                            .let {
//                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//                            }
//                    }
//                }
//            }
//        }
//    }
}