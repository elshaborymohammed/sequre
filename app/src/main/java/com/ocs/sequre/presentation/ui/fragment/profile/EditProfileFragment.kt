package com.ocs.sequre.presentation.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.compact.picker.ImagePicker
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.UserProfileViewHolder
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_data.view.*
import kotlinx.android.synthetic.main.layout_tool_bar.view.*

class EditProfileFragment : BaseFragment() {
    private lateinit var viewHolder: UserProfileViewHolder
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_data
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder = UserProfileViewHolder(view)
        viewModel = ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)

        view.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        view.input_avatar.setOnClickListener { ImagePicker.pick(this) }
        view.update.setOnClickListener {
            subscribe(
                viewModel.update(viewHolder.get()).subscribe(::onSuccess, onError())
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                data?.apply { requireView().input_avatar.setImageURI(this.data) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.profile().subscribe {
                viewHolder.set(it)
            }
        )
    }

    override fun onDestroyView() {
        loading(false)
        super.onDestroyView()
    }
}