package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionGeneralAnswerBody
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_question_general_02.*
import kotlinx.android.synthetic.main.layout_second_opinion_question_yes_no.view.*

class SecondOpinionQuestionsGeneralFirstFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_question_general_01
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)

        submit.setOnClickListener {
            subscribe(
                viewModel.put(
                    SecondOpinionGeneralAnswerBody(
                        generalQ1Id = yes_no_1.yes_no_question.tag as Int,
                        generalQ1Answer = if (yes_no_1.yes.isSelected) 1 else 0,
                        generalQ2Id = yes_no_2.yes_no_question.tag as Int,
                        generalQ2Answer = if (yes_no_2.yes.isSelected) 1 else 0
                    )
                ).subscribe({
                    findNavController().navigate(R.id.action_secondOpinionQuestionsGeneralFirstFragment_to_secondOpinionQuestionsGeneralSecondFragment)
                }, onError())
            )
        }

        yes_no_1?.apply {
            yes?.apply {
                isSelected = true
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no_1.no.isSelected = !yes_no_1.no.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no_1.no)
                    postInvalidate()
                    yes_no_1.no.postInvalidate()
                }
            }

            no?.apply {
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no_1.yes.isSelected = !yes_no_1.yes.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no_1.yes)
                    postInvalidate()
                    yes_no_1.yes.postInvalidate()
                }
            }
        }

        yes_no_2?.apply {
            yes?.apply {
                isSelected = true
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no_2.no.isSelected = !yes_no_2.no.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no_2.no)
                    postInvalidate()
                    yes_no_2.no.postInvalidate()
                }
            }

            no?.apply {
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no_2.yes.isSelected = !yes_no_2.yes.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no_2.yes)
                    postInvalidate()
                    yes_no_2.yes.postInvalidate()
                }
            }
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.generalQuestions().subscribe({ list ->
                list.forEach {
                    when (it.order) {
                        1 -> {
                            yes_no_1.yes_no_question.apply {
                                text = it.name
                                tag = it.id
                            }
                        }
                        2 -> {
                            yes_no_2.yes_no_question.apply {
                                text = it.name
                                tag = it.id
                            }
                        }
                    }
                }
            }, onError())
        )
    }

    private fun setTextSelect(button: MaterialButton) {
        if (button.isSelected) {
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        } else {
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        }
    }
}