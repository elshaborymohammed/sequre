package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.fragment.auth.LandingFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_tool_bar.*
import kotlinx.android.synthetic.main.layout_tool_bar.view.*

class ProfileFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        pager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabs, pager) { tab, _ ->

        }.attach()

        view.toolbar.setNavigationOnClickListener { findNavController().navigateUp()}
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments: List<Fragment>

        init {
            fragments = arrayListOf(LandingFragment(), LandingFragment(), LandingFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}