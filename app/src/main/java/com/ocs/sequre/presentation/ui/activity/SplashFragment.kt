package com.ocs.sequre.presentation.ui.activity

import androidx.navigation.fragment.findNavController
import com.jakewharton.rxrelay2.PublishRelay
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.preference.AuthPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    @Inject
    lateinit var auth: AuthPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            PublishRelay.just(auth.hasToken())
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturnItem(true)
                .subscribe {
//                    if (it)
//                        findNavController().navigate(R.id.action_splashFragment_to_navigationFragment)
//                    else
//                        findNavController().navigate(R.id.action_splashFragment_to_auth_graph)
                }
        )
    }
}