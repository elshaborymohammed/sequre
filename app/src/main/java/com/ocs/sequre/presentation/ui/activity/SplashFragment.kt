package com.ocs.sequre.presentation.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.preference.AuthPreference
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    @Inject
    lateinit var auth: AuthPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            if (auth.hasToken())
                findNavController().navigate(R.id.action_splashFragment_to_navigationFragment)
            else
                findNavController().navigate(R.id.action_splashFragment_to_auth_graph)
        }, 2000L)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}