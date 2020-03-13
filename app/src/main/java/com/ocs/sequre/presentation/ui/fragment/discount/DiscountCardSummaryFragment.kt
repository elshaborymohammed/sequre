package com.ocs.sequre.presentation.ui.fragment.discount

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.presentation.ui.adapter.OfferAdapter
import com.ocs.sequre.presentation.viewmodel.DiscountCardViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_discount_card_summary.*
import kotlinx.android.synthetic.main.layout_slider.*

class DiscountCardSummaryFragment : BaseBottomSheet() {
    private lateinit var adapter: OfferAdapter
    private lateinit var viewModel: DiscountCardViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_discount_card_summary
    }

    override fun onViewBound(view: View) {
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(DiscountCardViewModel::class.java)
        buy.setOnClickListener {
            findNavController().navigate(R.id.action_discountCardSummaryFragment_to_purchaseSuccessfullyFragment)
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.offers().subscribe({
                OfferAdapter(it).apply {
                    adapter = this
                    offers.adapter = adapter
                    TabLayoutMediator(tabs, offers) { tab, _ ->
                        tab.setIcon(R.drawable.se_tab)
                    }.attach()
                    setOnItemClickListener {
                        findNavController().navigate(R.id.action_discountCardSummaryFragment_to_offerFragment)
                    }
                }
            }, ::println)
        )
    }
}