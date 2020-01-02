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
        menu_nav.adapter = adapter
        adapter.swap(MenuAdapter.Menu.getMenus())

        menu_close.setOnClickListener { findNavController().navigateUp() }
    }
}