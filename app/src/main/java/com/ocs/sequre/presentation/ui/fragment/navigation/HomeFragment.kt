package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.navigation.Navigation
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_search_bar.view.*

class HomeFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        view.second_opinion.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_second_opinion_navigation))
        view.discount_card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_discount_card_navigation))
        view.notification.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_notificationsFragment))
        //view.notification.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.secondOpinionDoctorsFragment))
    }
}