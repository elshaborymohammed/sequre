package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.layout_second_opinion_ask.view.*

class SecondOpinionFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        view.for_you.setOnClickListener {
            findNavController().navigate(R.id.action_secondOpinionFragment_to_secondOpinionChooseSpecialityFragment)
        }

        view.for_other.setOnClickListener {
            findNavController().navigate(R.id.action_secondOpinionFragment_to_dependentsSummeryFragment)
        }

    }
}