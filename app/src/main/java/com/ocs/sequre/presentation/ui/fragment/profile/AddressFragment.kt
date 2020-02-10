package com.ocs.sequre.presentation.ui.fragment.profile

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.compact.app.extensions.text
import com.ocs.sequre.R
import com.ocs.sequre.app.GlideApp
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.domain.entity.ProfileData
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.card_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_address.view.*
import kotlinx.android.synthetic.main.fragment_profile_address.view.next
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*

class AddressFragment : BaseFragment() {
    private lateinit var viewModel: ProfileViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_profile_address
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        viewModel =
            ViewModelProviders.of(requireActivity(), factory).get(ProfileViewModel::class.java)

        view.next.setOnClickListener {
            subscribe(
                viewModel.update(
                    ProfileData(
                        country = view.input_country.text().toString(),
//                        city = view.input_city.text().toString(),
                        area = view.input_area.text().toString(),
                        street = view.input_street.text().toString()
                    )
                ).subscribe({}, onError())
            )
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading().subscribe(::loading),
            viewModel.data().subscribe {
                view?.apply {
                    input_country.editText!!.setText(it.country)
                    input_city.editText!!.setText(it.city)
                    input_area.editText!!.setText(it.area)
                    input_street.editText!!.setText(it.street)
                }
            }
        )
    }
}