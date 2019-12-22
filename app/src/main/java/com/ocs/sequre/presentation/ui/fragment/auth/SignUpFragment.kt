package com.ocs.sequre.presentation.ui.fragment.auth

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.SignUpViewHolder
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import kotlinx.android.synthetic.main.fragment_auth_sign_up.view.*

class SignUpFragment : BaseFragment() {
    private lateinit var signUpViewHolder: SignUpViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_auth_sign_up
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        signUpViewHolder = SignUpViewHolder(view)

        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        view.next.setOnClickListener {
            findNavController().navigate(
                R.id.action_signUpFragment_to_verificationFragment,
                VerificationFragmentArgs(signUpViewHolder.get()).toBundle()
            )
        }

        view.sign_in.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_signUpFragment_to_signInFragment
            )
        )
    }
}