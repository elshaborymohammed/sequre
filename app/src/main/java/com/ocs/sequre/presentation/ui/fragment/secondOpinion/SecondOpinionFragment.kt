package com.ocs.sequre.presentation.ui.fragment.secondOpinion

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.response.ApiException
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.response.error.ResponseErrorSolo
import com.ocs.sequre.domain.entity.Question
import com.ocs.sequre.domain.entity.SecondOpinion
import com.ocs.sequre.domain.entity.Speciality
import com.ocs.sequre.presentation.ui.adapter.SecondOpinionAdapter
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_second_opinion.view.*
import kotlinx.android.synthetic.main.layout_second_opinion_app_bar.*
import java.io.IOException

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
            }, onError()),
            secondOpinionViewModel.get().subscribe({
                adapter.add(askForWho(it))
                getSpecialities(it) {
                    subscribe(painQuestions())
                }
            }, {
                it.printStackTrace()
                try {
                    if (it is ApiException) {
                        if (it.code() >= 400) {
                            it.error(ResponseErrorSolo::class.java)?.run {
                                adapter.add(askForWho())
                            }
                        }
                    } else if (it is IOException) {
                        onIOException()
                    } else {
                        onError(it.message!!)
                    }
                } catch (e: Exception) {
                    onIOException()
                }
            })
        )
    }

    private fun askForWho(it: SecondOpinion.Body.Data? = null): SecondOpinion.Request.AskForWho =
        SecondOpinion.Request.AskForWho(it, {
            secondOpinionViewModel.body.forWho = SecondOpinion.Body.FOR_ME
            subscribe(getSpecialities())
        }, {
            secondOpinionViewModel.showSpeciality()
                .subscribe({ subscribe(getSpecialities()) }, ::println)
            secondOpinionViewModel.body.forWho = SecondOpinion.Body.FOR_OTHER
            findNavController().navigate(R.id.action_secondOpinionFragment_to_dependentsSummeryFragment)
        })

    private fun getSpecialities(it: SecondOpinion.Body.Data? = null, onNext: (() -> Unit)? = null) =
        secondOpinionViewModel.specialities()
            .subscribe({ specialities ->
                adapter.add(chooseSpeciality(it, specialities))
                onNext?.let { onNext() }
            }, onError())

    private fun chooseSpeciality(
        it: SecondOpinion.Body.Data? = null,
        specialities: List<Speciality>
    ): SecondOpinion.Request.ChooseSpeciality =
        SecondOpinion.Request.ChooseSpeciality(
            it,
            specialities
        ) { specialityId: Int, painId: Int, description: String ->
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
            var previousQuestion: Question? = null
            it.forEach { question ->
                when (question.order) {
                    1 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(question) { question: Question, answer: Int ->
                                secondOpinionViewModel.speciality.painQ1Id = question.id
                                secondOpinionViewModel.speciality.painQ1Answer = answer
                                adapter.next()
                            }
                        )
                        previousQuestion = question
                    }
                    2 -> {
                        adapter.add(
                            SecondOpinion.Request.MultiChoice(question) { question: Question, answers: List<String> ->
                                secondOpinionViewModel.speciality.painQ2Id = question.id
                                secondOpinionViewModel.speciality.painQ2Answer = answers

                                subscribe(
                                    secondOpinionViewModel.put(secondOpinionViewModel.speciality)
                                        .subscribe({ subscribe(generalQuestions()) }, onError())
                                )
                            },
                            !previousQuestion?.answer.isNullOrEmpty()
                        )

                        if (!question.answer.isNullOrEmpty()) {
                            subscribe(
                                secondOpinionViewModel.put(secondOpinionViewModel.speciality)
                                    .subscribe({ subscribe(generalQuestions()) }, onError())
                            )
                        }
                    }
                }
            }
        }, onError())

    private fun generalQuestions(): Disposable = secondOpinionViewModel.generalQuestions()
        .subscribe({
            welcome_title.text = getString(R.string.you_are_doing_great)
            welcome_subtitle.text = getString(R.string.request_some_general_questions)
            var previousQuestion: Question? = null
            it.forEach { question ->
                when (question.order) {
                    1 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(
                                question
                            ) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ1Id = question.id
                                secondOpinionViewModel.general.generalQ1Answer = answer
                                adapter.next()
                            }
                        )
                        previousQuestion = question
                    }
                    2 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(
                                question
                            ) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ2Id = question.id
                                secondOpinionViewModel.general.generalQ2Answer = answer
                                adapter.next()
                            }, !previousQuestion?.answer.isNullOrEmpty()
                        )
                        previousQuestion = question
                    }
                    3 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(
                                question
                            ) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ3Id = question.id
                                secondOpinionViewModel.general.generalQ3Answer = answer
                                adapter.next()
                            }, !previousQuestion?.answer.isNullOrEmpty()
                        )
                        previousQuestion = question
                    }
                    4 -> {
                        adapter.add(
                            SecondOpinion.Request.YesNo(
                                question
                            ) { question: Question, answer: Int ->
                                secondOpinionViewModel.general.generalQ4Id = question.id
                                secondOpinionViewModel.general.generalQ4Answer = answer
                                adapter.next()
                            }, !previousQuestion?.answer.isNullOrEmpty()
                        )
                        previousQuestion = question
                    }
                    5 -> {
                        adapter.add(
                            SecondOpinion.Request.MultiChoice(
                                question
                            ) { question: Question, answers: List<String> ->
                                secondOpinionViewModel.general.generalQ5Id = question.id
                                secondOpinionViewModel.general.generalQ5Answer = answers
                                subscribe(
                                    secondOpinionViewModel.put(secondOpinionViewModel.general)
                                        .subscribe({
                                            findNavController().navigate(R.id.action_secondOpinionFragment_to_medicalDocumentsFragment)
                                        }, onError())
                                )
                            },
                            !previousQuestion?.answer.isNullOrEmpty()
                        )

                        if (!question.answer.isNullOrEmpty()) {
                            subscribe(
                                secondOpinionViewModel.put(secondOpinionViewModel.general)
                                    .subscribe({
                                        findNavController().navigate(R.id.action_secondOpinionFragment_to_medicalDocumentsFragment)
                                    }, onError())
                            )
                        }
                    }
                }
            }
        }, onError())

    override fun onDestroy() {
        requireActivity().viewModelStore.clear()
        super.onDestroy()
    }
}