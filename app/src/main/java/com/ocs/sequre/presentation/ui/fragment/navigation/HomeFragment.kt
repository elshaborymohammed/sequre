package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.compact.app.extensions.phone
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.layout_search_bar.view.*
import kotlinx.android.synthetic.main.layout_tool_bar_home.*
import kotlin.system.exitProcess

class HomeFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_navigation
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitProcess(0)
                }

            })

        (activity !! as AppCompatActivity).setSupportActionBar(toolbar)
        (activity !! as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity !! as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }

//        val drawerToggle = ActionBarDrawerToggle(
//            activity,
//            drawer_layout,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawer_layout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()

        view.search.phone(R.string.app_name)
        val navController = Navigation.findNavController(activity !!, R.id.nav_host_fragment)
        bottom_navigation.setupWithNavController(navController)
        navigation_view.setupWithNavController(navController)
    }
}