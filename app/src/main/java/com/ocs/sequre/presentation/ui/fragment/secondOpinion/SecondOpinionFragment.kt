package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.Question
import com.ocs.sequre.domain.entity.SecondOpinion
import com.ocs.sequre.domain.entity.Speciality
import com.ocs.sequre.presentation.ui.adapter.SecondOpinionAdapter
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion.view.*
import kotlinx.android.synthetic.main.layout_second_opinion_app_bar.*

class SecondOpinionFragment : BaseFragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var secondOpinionViewModel: SecondOpinionViewModel
    private lateinit var adapter: SecondOpinionAdapter

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        profileViewModel =
            ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)
        secondOpinionViewModel =
            ViewModelProvider(requireActivity(), factory).get(SecondOpinionViewModel::class.java)

        SecondOpinionAdapter().apply {
            adapter = this
            view.recyclerView.adapter = adapter
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            profileViewModel.loading().subscribe(::loading),
            secondOpinionViewModel.loading().subscribe(::loading),
            profileViewModel.profile().subscribe({
                welcome_title.text = getString(R.string.good_morning, it.name)
                welcome_subtitle.text = getString(R.string.we_are_pleased_to_help_you)
                adapter.add(askForWho())
            }, onError())
        )
    }

    private fun getSpecialities() = secondOpinionViewModel.specialities()
        .subscribe({ specialities ->
            adapter.add(chooseSpeciality(specialities))
        }, onError())

    private fun askForWho(): SecondOpinion.Request.AskForWho =
        SecondOpinion.Request.AskForWho({
            secondOpinionViewModel.body.forWho = SecondOpinion.Body.FOR_ME
            subscribe(getSpecialities())
        }, {
            secondOpinionViewModel.showSpeciality()
                .subscribe({ subscribe(getSpecialities()) }, ::println)
            secondOpinionViewModel.body.forWho = SecondOpinion.Body.FOR_OTHER
            findNavController().navigate(R.id.action_secondOpinionFragment_to_dependentsSummeryFragment)
        })

    private fun chooseSpeciality(specialities: List<Speciality>): SecondOpinion.Request.ChooseSpeciality =
        SecondOpinion.Request.ChooseSpeciality(specialities) { specialityId: Int, painId: Int, description: String ->
            secondOpinionViewModel.body.specialityId = specialityId
            secondOpinionViewModel.body.painId = painId
            secondOpinionViewModel.body.description = description
            subscribe(
                secondOpinionViewModel.post(secondOpinionViewModel.body)
                    .subscribe({ subscribe(painQuestions()) }, onError())
            )
        }

    private fun painQuestions(): Disposable = secondOpinionViewModel.painQuestions()
        .subscribe({
            welcome_title.text = getString(R.string.great)
            welcome_subtitle.text = getString(R.string.keep_going_answer_the_questions)
            it.forEach { question ->
                when (question.order) {
                    1 -> {
                        adapter.add(SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                            secondOpinionViewModel.speciality.painQ1Id = question.id
                            secondOpinionViewModel.speciality.painQ1Answer = answer
                            adapter.next()
                        })
                    }
                    2 -> {
                        adapter.add(
                            SecondOpinion.Request.MultiChoice(question) { question: Question, answers: List<Int> ->
                                secondOpinionViewModel.speciality.painQ2Id = question.id
                                secondOpinionViewModel.speciality.painQ2Answer = answers

                                subscribe(
                                    secondOpinionViewModel.put(secondOpinionViewModel.speciality)
                                        .subscribe({ subscribe(generalQuestions()) }, onError())
                                )
                            },
                            false
                        )
                    }
                }
            }
        }, onError())

    private fun generalQuestions(): Disposable = secondOpinionViewModel.generalQuestions()
        .subscribe({
            welcome_title.text = getString(R.string.you_are_doing_great)
            welcome_subtitle.text = getString(R.string.request_some_general_questions)

            it.forEach { question ->
                when (question.order) {
                    1 -> {
                        adapter.add(SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                            secondOpinionViewModel.general.generalQ1Id = question.id
                            secondOpinionViewModel.general.generalQ1Answer = answer
                            adapter.next()
                        })
                    }
                    2 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ2Id = question.id
                                secondOpinionViewModel.general.generalQ2Answer = answer
                                adapter.next()
                            }, false
                        )
                    }
                    3 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ3Id = question.id
                                secondOpinionViewModel.general.generalQ3Answer = answer
                                adapter.next()
                            }, false
                        )
                    }
                    4 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ4Id = question.id
                                secondOpinionViewModel.general.generalQ4Answer = answer
                                adapter.next()
                            }, false
                        )
                    }
                    5 -> {
                        adapter.add(
                            SecondOpinion.Request.MultiChoice(question) { question: Question, answers: List<Int> ->
                                secondOpinionViewModel.general.generalQ5Id = question.id
                                secondOpinionViewModel.general.generalQ5Answer = answers
                                subscribe(
                                    secondOpinionViewModel.put(secondOpinionViewModel.general)
                                        .subscribe({
                                            findNavController().navigate(R.id.action_secondOpinionFragment_to_medicalDocumentsFragment)
                                        }, onError())
                                )
                            },
                            false
                        )
                    }
                }
            }
        }, onError())

    override fun onDestroy() {
        requireActivity().viewModelStore.clear()
        super.onDestroy()
    }
}