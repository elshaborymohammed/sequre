package com.ocs.sequre.presentation.ui.fragment.discount

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.OfferBenefitAdapter
import com.ocs.sequre.presentation.viewmodel.DiscountCardViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_discount_card_summary.*
import kotlinx.android.synthetic.main.layout_slider.*

class OfferFragment : BaseFragment(R.string.offer_details) {

    private lateinit var adapter: OfferBenefitAdapter
    private lateinit var viewModel: DiscountCardViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_discount_card_offer
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        viewModel =
            ViewModelProvider(requireActivity(), factory).get(DiscountCardViewModel::class.java)
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.offers().subscribe({
                OfferBenefitAdapter(it).apply {
                    adapter = this
                    offers.adapter = adapter
                    TabLayoutMediator(tabs, offers) { tab, _ ->
                        tab.setIcon(R.drawable.se_tab)
                    }.attach()
                }
            }, ::println)
        )
    }
}