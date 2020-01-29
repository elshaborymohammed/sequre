package com.ocs.sequre.presentation.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment

class SplashFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_auth_graph)
        }, 2000L)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }
}