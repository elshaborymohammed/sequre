package com.ocs.sequre.app.base

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.compact.app.CompactFragment
import javax.inject.Inject

abstract class BaseFragment : CompactFragment() {
    @Inject
    protected lateinit var factory: ViewModelProvider.Factory

//    @Inject
//    protected lateinit var auth: AuthenticationPreference

    override fun onViewBound(view: View) {

    }

    protected fun setToolBar(toolbar: Toolbar) {
//        (activity as AppCompatActivity)?.apply {
//            setSupportActionBar(toolbar)
//        }
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