package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.navigation.Navigation
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        view.second_opinion.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_secondOpinionFragment))
    }
}