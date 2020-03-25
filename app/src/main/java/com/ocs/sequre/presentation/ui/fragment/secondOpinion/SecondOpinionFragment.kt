package com.ocs.sequre.presentation.ui.fragment.secondopinion

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_second_opinion_app_bar.*
import kotlinx.android.synthetic.main.layout_second_opinion_ask.view.*

class SecondOpinionFragment : BaseFragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_second_opinion
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        javax.inject.Provider { }

        viewModel = ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)
        view.for_you.setOnClickListener {
            findNavController().navigate(
                R.id.action_secondOpinionFragment_to_secondOpinionChooseSpecialityFragment,
                SecondOpinionChooseSpecialityFragmentArgs().toBundle()
            )
        }

        view.for_other.setOnClickListener {
            findNavController().navigate(R.id.action_secondOpinionFragment_to_dependentsSummeryFragment)
        }

    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.profile().subscribe({
                welcome_title.text = getString(R.string.good_morning, it.name)
                welcome_subtitle.text = getString(R.string.we_are_pleased_to_help_you)
            }, onError())
        )
    }

    override fun onDestroy() {
        requireActivity().viewModelStore.clear()
        super.onDestroy()
    }
}