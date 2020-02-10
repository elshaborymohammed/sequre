package com.ocs.sequre.presentation.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.compact.picker.ImagePicker
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.UserProfileViewHolder
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*

class EditProfileFragment : BaseFragment() {
    private lateinit var viewHolder: UserProfileViewHolder
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_edit
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder = UserProfileViewHolder(view)
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(ProfileViewModel::class.java)

        view.input_avatar.setOnClickListener {
            ImagePicker.pick(requireActivity())
        }

        view.next.setOnClickListener {
            subscribe(
                viewModel.update(viewHolder.get()).subscribe({}, onError())
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("requestCode = [$requestCode], resultCode = [$resultCode]")

        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                if (data != null) {
                    println("data = [${data}]")
                    requireView().input_avatar.setImageURI(data.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.data().subscribe {
                viewHolder.set(it)
            }
        )
    }
}