package com.ocs.sequre.presentation.ui.fragment.provider

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.fragment.auth.LandingFragment
import kotlinx.android.synthetic.main.fragment_navigation.*

class ServiceProviderFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_service_provider
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        PagerAdapter(this).apply {
            pager.adapter = this
            TabLayoutMediator(tabs, pager) { tab, position ->
                tab.setText(titles[position])
            }.attach()
        }
    }

    private inner class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments: List<Fragment>
        internal val titles: List<Int>

        init {
            fragments = arrayListOf(ServiceProvidersFragment(), MapsFragment())
            titles = arrayListOf(R.string.list, R.string.map)
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}