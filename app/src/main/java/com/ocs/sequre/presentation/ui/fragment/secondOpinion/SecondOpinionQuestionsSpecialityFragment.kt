package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionSpecialityAnswerBody
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion_question_speciality.*
import kotlinx.android.synthetic.main.layout_second_opinion_question_multi_choice.view.*
import kotlinx.android.synthetic.main.layout_second_opinion_question_yes_no.view.*

class SecondOpinionQuestionsSpecialityFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_question_speciality
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(activity!!, factory).get(SecondOpinionViewModel::class.java)

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
                    SecondOpinionSpecialityAnswerBody(
                        painQ1Id = yes_no.yes_no_question.tag as Int,
                        painQ1Answer = if (yes_no.yes.isSelected) 1 else 0,
                        painQ2Id = multi_choice.multi_choice_question.tag as Int,
                        painQ2Answer = list
                    )
                ).subscribe({
                    findNavController().navigate(R.id.action_secondOpinionQuestionsSpecialityFragment_to_secondOpinionQuestionsGeneralFirstFragment)
                }, onError())
            )
        }

        yes_no?.apply {
            yes?.apply {
                isSelected = true
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no.no.isSelected = !yes_no.no.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no.no)
                    postInvalidate()
                    yes_no.no.postInvalidate()
                }
            }

            no?.apply {
                setTextSelect(this)
                setOnClickListener {
                    isSelected = !isSelected
                    yes_no.yes.isSelected = !yes_no.yes.isSelected
                    setTextSelect(this)
                    setTextSelect(yes_no.yes)
                    postInvalidate()
                    yes_no.yes.postInvalidate()
                }
            }
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.painQuestions().subscribe({ list ->
                list.forEach {
                    when (it.order) {
                        1 -> {
                            yes_no.yes_no_question.apply {
                                text = it.name
                                tag = it.id
                            }
                        }
                        2 -> {
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