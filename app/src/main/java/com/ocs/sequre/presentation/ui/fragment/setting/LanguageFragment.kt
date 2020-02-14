package com.ocs.sequre.presentation.ui.fragment.setting

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_language.view.*

class LanguageFragment : BaseFragment() {

    private lateinit var viewModel: UserViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_language
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        view.next.setOnClickListener {
            if (viewModel.setLang(resources.getStringArray(R.array.lang_array)[view.language.selectedItemPosition])) {
//                findNavController().navigate(R.id.action_languageFragment_to_landingFragment)
            }
        }
    }
}