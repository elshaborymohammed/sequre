package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_second_opinion_question_general_01.view.*

class SecondOpinionQuestionsGeneralFirstFragment : BaseFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_question_general_01
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        view.submit.setOnClickListener {
            findNavController().navigate(R.id.action_secondOpinionQuestionsGeneralFirstFragment_to_secondOpinionQuestionsGeneralSecondFragment)
        }

    }
}