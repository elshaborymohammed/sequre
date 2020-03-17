package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.viewmodel.SecondOpinionViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_second_opinion_speciality.*
import kotlinx.android.synthetic.main.layout_second_opinion_speciality.view.*

class SecondOpinionChooseSpecialityFragment : BaseFragment() {

    private lateinit var viewModel: SecondOpinionViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion_choose_speciality
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel = ViewModelProvider(this, factory).get(SecondOpinionViewModel::class.java)

        view.save.setOnClickListener {
            findNavController().navigate(R.id.action_secondOpinionChooseSpecialityFragment_to_secondOpinionQuestionsSpecialityFragment)
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
                            setTag(1, it[position].id)
                        }
                    }
                }
            }, onError())
        )
    }
}