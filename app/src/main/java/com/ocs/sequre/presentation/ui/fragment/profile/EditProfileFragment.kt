package com.ocs.sequre.presentation.ui.fragment.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.compact.picker.ImagePicker
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.viewholder.UserProfileViewHolder
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_profile_data.*
import kotlinx.android.synthetic.main.fragment_profile_data.view.*

class EditProfileFragment : BaseFragment() {
    protected lateinit var authViewModel: AuthViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var viewHolder: UserProfileViewHolder

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_data
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewHolder = UserProfileViewHolder(view)
        authViewModel =
            ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        profileViewModel =
            ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)

        view.input_avatar.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    ImagePicker.PERMISSIONS[0]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    ImagePicker.PERMISSIONS,
                    ImagePicker.REQUEST_CODE
                )
            } else {
                ImagePicker.build(this)
            }
        }
        view.update.setOnClickListener {
            subscribe(
                profileViewModel.update(viewHolder.get()).subscribe(::onSuccess, onError())
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ImagePicker.REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.build(this)
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                when {
                    data!!.hasExtra("data") -> {
                        GlideApp.with(this).load(data.extras!!["data"] as Bitmap).into(input_avatar)
                    }
                    null != data.data -> {
                        GlideApp.with(this).load(data.data).into(input_avatar)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        loading(false)
        super.onDestroyView()
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            profileViewModel.loading().subscribe(::loading),
            authViewModel.countryCode().subscribe({
                viewHolder.setCountries(it)
                subscribe(profileViewModel.profile().subscribe(viewHolder::set))
                subscribe(
                    viewHolder.validations().subscribe(
                        requireView().update::setEnabled,
                        Throwable::printStackTrace
                    )
                )
            }, onError())
        )
    }
}