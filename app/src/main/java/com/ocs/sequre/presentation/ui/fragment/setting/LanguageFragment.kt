package com.ocs.sequre.presentation.ui.fragment.setting

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.ToolBarViewHolder
import com.ocs.sequre.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_language.view.*
import java.util.*

class LanguageFragment : BaseFragment() {

    private lateinit var viewModel: UserViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_language
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val toolBarViewHolder = ToolBarViewHolder(view)
        setToolBar(toolBarViewHolder.toolbar)

        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        view.next.setOnClickListener {
            val lang =
                resources.getStringArray(R.array.lang_array)[view.language.selectedItemPosition]
            if (viewModel.setLang(lang)) {
                Locale.setDefault(Locale(lang))
                findNavController().navigate(R.id.action_languageFragment_to_signInFragment)
            }
        }
    }
}