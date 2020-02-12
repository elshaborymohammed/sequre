package com.ocs.sequre.presentation.ui.fragment.profile

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.data.remote.model.response.error.Error
import com.ocs.sequre.data.remote.model.response.error.ErrorStatus
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.presentation.ui.viewholder.DependentViewHolder
import com.ocs.sequre.presentation.viewmodel.DependentViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_tool_bar.view.*

abstract class DependentFragment : BaseFragment() {

    protected lateinit var dependentViewModel: DependentViewModel
    protected lateinit var viewHolder: DependentViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_data
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder = DependentViewHolder(view)

        dependentViewModel =
            ViewModelProviders.of(this, factory).get(DependentViewModel::class.java)

        view.save.setOnClickListener {
            onSaveClicked(viewHolder.get())
        }

        view.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            dependentViewModel.loading().subscribe(::loading),
            viewHolder.validations().subscribe(requireView().save::setEnabled)
        )
    }

    abstract fun onSaveClicked(it: Dependent)
}