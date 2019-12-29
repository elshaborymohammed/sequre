package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.fragment.SplashFragment
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_navigation
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        pager.adapter = PagerAdapter(this)

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.setIcon(R.drawable.se_tab_home)
        }.attach()

        tabs.getTabAt(1)?.select()
    }


    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments: List<Fragment>

        init {
            fragments = arrayListOf(HomeFragment(), HomeFragment(), HomeFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}
