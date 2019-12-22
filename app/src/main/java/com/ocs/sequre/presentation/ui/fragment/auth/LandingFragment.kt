package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.navigation.Navigation
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth_landing.view.*

class LandingFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_landing
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        view.sign_in.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_landingFragment_to_signInFragment)
        )

        view.sign_up.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_landingFragment_to_signUpFragment)
        )
    }
}