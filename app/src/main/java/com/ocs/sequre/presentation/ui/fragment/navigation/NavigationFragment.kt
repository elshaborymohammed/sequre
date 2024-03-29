package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.fragment.auth.LandingFragment
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment() {
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun layoutRes(): Int {
        return R.layout.fragment_navigation
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        pager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabs, pager) { tab, _ ->
            tab.setIcon(R.drawable.se_tab_home)
        }.attach()
        pager.setCurrentItem(1, false)
        bottom_bar.setNavigationOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigationFragment_to_menuFragment))
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments: List<Fragment>

        init {
            fragments = arrayListOf(LandingFragment(), HomeFragment(), LandingFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}
