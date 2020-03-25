package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody
import com.ocs.sequre.domain.entity.Pain
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_second_opinion_speciality.*

class SecondOpinionChooseSpecialityFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel
    private var dependentId: Int = 0
    private var forWho: Int = 0

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_choose_speciality
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)

        arguments?.run {
            SecondOpinionChooseSpecialityFragmentArgs.fromBundle(this)
                .let {
                    dependentId = it.dependentId
                    forWho = it.forWho
                }
        }

        save.setOnClickListener {
            try {
                subscribe(
                    viewModel.post(
                        SecondOpinionBody(
                            forOther = forWho,
                            dependentId = dependentId,
                            specialityId = speciality.tag as Int,
                            painId = (pain.selectedItem as Pain).id,
                            date = "2020-02-02",
                            description = description.text.toString()
                        )
                    ).subscribe({
                        findNavController().navigate(R.id.action_secondOpinionChooseSpecialityFragment_to_secondOpinionQuestionsSpecialityFragment)
                    }, onError())
                )
            } catch (e: TypeCastException) {
                e.printStackTrace()
                Snackbar.make(
                    requireView(),
                    "You must select speciality",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.specialities().subscribe({
                speciality.run {
                    onItemSelectedListener = object : OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            println("parent = [${parent}]")
                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            println("parent = [${parent}], view = [${view}], position = [${position}], id = [${id}]")
                        }
                    }

                    threshold = 1 //will start working from first character
                    ArrayAdapter(
                        context,
                        R.layout.select_dialog_item,
                        R.id.text,
                        it.map { it.name }
                    ).run {
                        setAdapter(this)
                        setOnItemClickListener { _, _, position, _ ->
                            setText(it[position].name, false)
                            tag = it[position].id
                            isEnabled = false

                            pain.run {
                                ArrayAdapter(
                                    context,
                                    android.R.layout.simple_spinner_item,
                                    android.R.id.text1,
                                    it[position].pains
                                ).run {
                                    setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                                    adapter = this
                                }
                            }
                        }
                    }
                }
            }, onError())
        )
    }

}