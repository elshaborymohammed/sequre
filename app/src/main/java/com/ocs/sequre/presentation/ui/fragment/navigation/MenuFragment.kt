package com.ocs.sequre.presentation.ui.fragment.navigation

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.MenuAdapter
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_menu
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val adapter = MenuAdapter()
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_menu)
            .addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.profileFragment
                    || destination.id == R.id.editProfileFragment
                    || destination.id == R.id.dependentCreateFragment
                    || destination.id == R.id.dependentUpdateFragment
                ) {
                    menu_nav.visibility = View.GONE
                } else {
                    menu_nav.visibility = View.VISIBLE
                }
            }

        menu_nav.adapter = adapter
        adapter.setOnItemClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_menu)
                .navigate(it)
        }
        menu_close.setOnClickListener { findNavController().navigateUp() }
    }
}