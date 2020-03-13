package com.ocs.sequre.presentation.ui.activity

import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxrelay2.PublishRelay
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.preference.LanguageStatusPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchFragment : BaseFragment() {

    @Inject
    lateinit var auth: AuthPreference

    @Inject
    lateinit var lang: LanguageStatusPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun onViewBound(view: View) {
//        requireActivity().window.decorView.setBackgroundColor(Color.WHITE)
        super.onViewBound(view)
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            PublishRelay.just(auth.hasToken())
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturnItem(true)
                .subscribe {
                    if (auth.hasToken()) {
                        findNavController().navigate(R.id.action_splashFragment_to_navigationFragment)
                    } else {
                        if (lang.hasLang()) {
                            findNavController().navigate(R.id.action_splashFragment_to_auth_graph)

                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_languageFragment)
                        }
                    }
                }
        )
    }
}