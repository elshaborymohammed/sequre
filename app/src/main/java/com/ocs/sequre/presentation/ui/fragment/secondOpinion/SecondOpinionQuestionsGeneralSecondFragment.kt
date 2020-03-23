package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionGeneralAnswerBody
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_question_general_02.*
import kotlinx.android.synthetic.main.layout_second_opinion_question_multi_choice.view.*
import kotlinx.android.synthetic.main.layout_second_opinion_question_yes_no.view.*

class SecondOpinionQuestionsGeneralSecondFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_question_general_02
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)

        submit.setOnClickListener {
            val list: ArrayList<Int> = ArrayList()

            for (i in 1..3) {
                view.findViewWithTag<MaterialCheckBox>("choose_$i")
                    .apply {
                        if (isChecked)
                            list.add(getTag(R.id.second_opinion).toString().toInt())
                    }
            }
            subscribe(
                viewModel.put(
                    SecondOpinionGeneralAnswerBody(
                        generalQ3Id = yes_no_1.yes_no_question.tag as Int,
                        generalQ3Answer = if (yes_no_1.yes.isSelected) 1 else 0,
                        generalQ4Id = yes_no_2.yes_no_question.tag as Int,
                        generalQ4Answer = if (yes_no_2.yes.isSelected) 1 else 0,
                        generalQ5Id = multi_choice.multi_choice_question.tag as Int,
                        generalQ5Answer = list
                    )
                ).subscribe({
                    findNavController().navigate(R.id.action_secondOpinionQuestionsGeneralSecondFragment_to_secondOpinionMedicalDocumentFragment)
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
                        3 -> {
                            yes_no_1.yes_no_question.apply {
                                text = it.name
                                tag = it.id
                            }
                        }
                        4 -> {
                            yes_no_2.yes_no_question.apply {
                                text = it.name
                                tag = it.id
                            }
                        }
                        5 -> {
                            multi_choice.multi_choice_question.apply {
                                text = it.name
                                tag = it.id
                            }
                            for (i in 0..it.fields.size) {
                                multi_choice.findViewWithTag<MaterialCheckBox>("choose_${i + 1}")
                                    ?.run {
                                        setTag(R.id.second_opinion, it.fields[i].key)
                                        text = it.fields[i].value
                                    }
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