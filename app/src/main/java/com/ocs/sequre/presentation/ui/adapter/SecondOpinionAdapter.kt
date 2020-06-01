package com.ocs.sequre.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compact.widget.recyclerview.SpaceDecoration
import com.google.android.material.checkbox.MaterialCheckBox
import com.ocs.sequre.R
import com.ocs.sequre.app.base.isBlack
import com.ocs.sequre.app.base.setMaterialButtonTextSelect
import com.ocs.sequre.app.base.setTextSelect
import com.ocs.sequre.domain.entity.Pain
import com.ocs.sequre.domain.entity.SecondOpinion
import kotlinx.android.synthetic.main.card_second_opinion_ask.view.*
import kotlinx.android.synthetic.main.card_second_opinion_choose_speciality.view.*
import kotlinx.android.synthetic.main.card_second_opinion_question_multi_choice.view.*
import kotlinx.android.synthetic.main.card_second_opinion_question_multi_choice.view.submit
import kotlinx.android.synthetic.main.card_second_opinion_question_yes_no.view.*
import kotlinx.android.synthetic.main.card_second_opinion_question_yes_no.view.question
import java.util.*

class SecondOpinionAdapter :
    RecyclerView.Adapter<SecondOpinionAdapter.ViewHolder<SecondOpinion.Request>>() {

    private lateinit var recyclerView: RecyclerView
    private var data = ArrayList<SecondOpinion.Request>()
    private var next = ArrayList<SecondOpinion.Request>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(
            SpaceDecoration.builder(recyclerView.context).horizontal(24).vertical(8).build()
        )
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<SecondOpinion.Request> {
        return when (viewType) {
            R.layout.card_second_opinion_ask -> ViewHolder.AskForWhoViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            R.layout.card_second_opinion_choose_speciality -> ViewHolder.ChooseSpecialityViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            R.layout.card_second_opinion_question_yes_no -> ViewHolder.YesNoQuestionViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> ViewHolder.MultiChoiceQuestionViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        } as ViewHolder<SecondOpinion.Request>
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is SecondOpinion.Request.AskForWho -> R.layout.card_second_opinion_ask
            is SecondOpinion.Request.ChooseSpeciality -> R.layout.card_second_opinion_choose_speciality
            is SecondOpinion.Request.YesNo -> R.layout.card_second_opinion_question_yes_no
            is SecondOpinion.Request.MultiChoice -> R.layout.card_second_opinion_question_multi_choice
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder<SecondOpinion.Request>, position: Int) {
        holder.bind(data[position], position, data.size - 1 > position)
    }

    fun swap(data: List<SecondOpinion.Request>) {
        this.data = data as ArrayList<SecondOpinion.Request>
        notifyDataSetChanged()
    }

    fun add(t: SecondOpinion.Request, next: Boolean = true) {
        if (next) {
            data.add(t)
            notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(data.size - 1)
        } else {
            this.next.add(t)
        }
    }

    fun next() {
        if (this.next.size > 0) {
            this.next.removeAt(0)?.apply {
                add(this)
            }
        }
    }

    sealed class ViewHolder<in T : SecondOpinion.Request>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        abstract fun bind(data: T, position: Int, hasNext: Boolean)

        class AskForWhoViewHolder(itemView: View) :
            ViewHolder<SecondOpinion.Request.AskForWho>(itemView) {

            override fun bind(
                body: SecondOpinion.Request.AskForWho,
                position: Int, hasNext: Boolean
            ) {
                itemView.apply {
                    for_you.isEnabled = !hasNext
                    for_other.isEnabled = !hasNext
                    for_you.setTextSelect()
                    for_other.setTextSelect()

                    body.answer?.apply {
                        for_you.isSelected = (forWho == SecondOpinion.Body.FOR_ME)
                        for_other.isSelected = (forWho == SecondOpinion.Body.FOR_OTHER)
                        for_you.setTextSelect()
                        for_other.setTextSelect()
                    }

                    if (!hasNext) {
                        for_you.setOnClickListener {
                            it.isSelected = true
                            it.setMaterialButtonTextSelect()
                            body.forMeListener()
                        }
                        for_other.setOnClickListener {
                            it.isSelected = true
                            it.setMaterialButtonTextSelect()
                            body.forOtherListener()
                        }
                    } else {
                        for_you.setOnClickListener {}
                        for_other.setOnClickListener {}
                    }
                }
            }
        }

        class ChooseSpecialityViewHolder(itemView: View) :
            ViewHolder<SecondOpinion.Request.ChooseSpeciality>(itemView) {

            override fun bind(
                body: SecondOpinion.Request.ChooseSpeciality,
                position: Int, hasNext: Boolean
            ) {
                itemView.speciality.isEnabled = !hasNext
                itemView.pain.isEnabled = !hasNext
                itemView.description.isEnabled = !hasNext
                itemView.submit.apply {
                    isEnabled = !hasNext
                    isSelected = !isEnabled
//                    setTextSelect()
                }


                itemView.speciality.apply {
                    body.specialities.let {
                        threshold = 1 //will start working from first character
                        ArrayAdapter(
                            context,
                            R.layout.select_dialog_item,
                            R.id.text,
                            it.map { it.name }
                        ).run {
                            setAdapter(this)

                            body.answer?.apply {
                                it.forEachIndexed { index, speciality ->
                                    if (speciality.id == specialityId) {
                                        tag = specialityId
                                        setText(speciality.name, false)
                                        itemView.description.setText(
                                            (description ?: "").replace(
                                                "empty",
                                                ""
                                            )
                                        )

                                        itemView.pain.run {
                                            ArrayAdapter(
                                                context,
                                                android.R.layout.simple_spinner_item,
                                                android.R.id.text1,
                                                speciality.pains
                                            ).run {
                                                setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                                                adapter = this
                                                itemView.pain.requestFocus()

                                                setSelection(index)
                                            }
                                        }
                                    }
                                }
                            }

                            setOnItemClickListener { _, _, position, _ ->
                                setText(it[position].name, false)
                                tag = it[position].id
                                isEnabled = false
                                itemView.pain.run {
                                    ArrayAdapter(
                                        context,
                                        android.R.layout.simple_spinner_item,
                                        android.R.id.text1,
                                        it[position].pains
                                    ).run {
                                        setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                                        adapter = this
                                        itemView.pain.requestFocus()
                                    }
                                }

                                itemView.submit.setOnClickListener { view ->
                                    body.listener(
                                        itemView.speciality.tag as Int,
                                        (itemView.pain.selectedItem as Pain).id,
                                        itemView.description.text.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        class YesNoQuestionViewHolder(itemView: View) :
            ViewHolder<SecondOpinion.Request.YesNo>(itemView) {
            override fun bind(body: SecondOpinion.Request.YesNo, position: Int, hasNext: Boolean) {
                body.hasNext = hasNext

                itemView.apply {
                    if (!body.isEditable) {
                        yes.isEnabled = !hasNext
                        no.isEnabled = !hasNext
                    } else {
                        yes.isEnabled = true
                        no.isEnabled = true
                    }
                    yes.setTextSelect()
                    no.setTextSelect()

                    question?.apply {
                        text = body.question.name
                        tag = body.question.id
                    }

                    if (!body.question.answer.isBlack()) {
                        body.question.answer!!.firstOrNull()?.let {
                            yes?.apply {
                                isSelected = (it == "0")
                                setTextSelect()
                                if (body.isEditable) {
                                    setOnClickListener {
                                        itemView.no.run {
                                            isSelected = false
                                            setTextSelect()
                                            postInvalidate()
                                        }

                                        isSelected = true
                                        setTextSelect()
                                        postInvalidate()
                                        body.question.answer = listOf("0")
                                        body.listener(body.question, 0, body.isEditable, hasNext)
                                    }
                                } else {
                                    setOnClickListener {}
                                }
                            }

                            no?.apply {
                                isSelected = (it == "1")
                                setTextSelect()
                                if (body.isEditable) {
                                    setOnClickListener {
                                        itemView.yes.run {
                                            isSelected = false
                                            setTextSelect()
                                            postInvalidate()
                                        }

                                        isSelected = true
                                        setTextSelect()
                                        postInvalidate()
                                        body.question.answer = listOf("1")
                                        body.listener(body.question, 1, body.isEditable, hasNext)
                                    }
                                } else {
                                    setOnClickListener {}
                                }
                            }
                        }
                    } else {
                        yes?.apply {
                            isSelected = false
                            setTextSelect()
                            setOnClickListener {
                                isSelected = true
                                setTextSelect()
                                postInvalidate()
                                body.question.answer = listOf("0")
                                body.listener(body.question, 0, body.isEditable, hasNext)
                            }
                        }

                        no?.apply {
                            isSelected = false
                            setTextSelect()
                            setOnClickListener {
                                isSelected = true
                                setTextSelect()
                                postInvalidate()
                                body.question.answer = listOf("1")
                                body.listener(body.question, 1, body.isEditable, hasNext)
                            }
                        }
                    }
                }
            }
        }

        class MultiChoiceQuestionViewHolder(itemView: View) :
            ViewHolder<SecondOpinion.Request.MultiChoice>(itemView) {
            override fun bind(
                body: SecondOpinion.Request.MultiChoice,
                position: Int,
                hasNext: Boolean
            ) {
                itemView.apply {
                    submit.isEnabled = true
                    choose_1.isEnabled = true
                    choose_2.isEnabled = true
                    choose_3.isEnabled = true

                    question?.apply {
                        text = body.question.name
                        tag = body.question.id
                    }

                    for (i in 0..body.question.fields.size) {
                        answers.findViewWithTag<MaterialCheckBox>("choose_${i + 1}")?.run {
                            if (!body.isEditable) {
                                isEnabled = !hasNext
                            }
                            isChecked = false
                            setTag(R.id.second_opinion, body.question.fields[i].key)
                            text = body.question.fields[i].value
                            body.question.answer?.run {
                                isChecked = contains(body.question.fields[i].key)
                            }
                        }
                    }

                    submit.apply {
                        if (!body.isEditable) {
                            isEnabled = !hasNext
                        }
                        isSelected = !isEnabled
//                        setTextSelect()

                        setOnClickListener {
                            val list: ArrayList<String> = ArrayList()
                            for (i in 1..3) {
                                itemView.answers.findViewWithTag<MaterialCheckBox>("choose_$i")
                                    .run {
                                        if (isChecked)
                                            list.add(getTag(R.id.second_opinion).toString())
                                    }
                            }
                            body.question.answer = list
                            body.listener(body.question, list, body.isEditable)
                        }
                    }
                }
            }
        }
    }
}
