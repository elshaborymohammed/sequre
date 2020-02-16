package com.ocs.sequre.presentation.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.view.View
<<<<<<< HEAD
import androidx.annotation.RequiresApi
=======
>>>>>>> f765368eadf7eed4caffbe2d45e0d8079f990b49
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.picker.ImagePicker
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
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
            ViewModelProvider(this, factory).get(DependentViewModel::class.java)

        view.input_avatar.setOnClickListener {
            ImagePicker.build(this)
        }
        view.update.setOnClickListener {
            onSaveClicked(viewHolder.get())
        }
        view.delete.setOnClickListener {
            subscribe(
                dependentViewModel.delete(viewHolder.get().id).subscribe(::onSuccess, onError())
            )
        }
        view.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                data?.apply { ImagePicker.setImage(requireView().input_avatar, this) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            dependentViewModel.loading().subscribe(::loading),
            viewHolder.validations().subscribe(requireView().update::setEnabled)
        )
    }

    abstract fun onSaveClicked(it: Dependent)
}