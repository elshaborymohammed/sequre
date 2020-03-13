package com.ocs.sequre.presentation.ui.fragment.discount

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseFragment
import com.ocs.sequre.presentation.ui.adapter.DiscountCardAdapter
import com.ocs.sequre.presentation.viewmodel.DiscountCardViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_list_items.view.*

class DiscountCardsFragment :
    BaseFragment(R.string.discount_card_title, R.string.discount_card_subtitle) {

    private lateinit var adapter: DiscountCardAdapter
    private lateinit var viewModel: DiscountCardViewModel

    override fun layoutRes(): Int {
        return R.layout.fragment_discount_card
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        viewModel =
            ViewModelProvider(requireActivity(), factory).get(DiscountCardViewModel::class.java)
        adapter = DiscountCardAdapter()
        adapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_discountCardsFragment_to_discountCardSummaryFragment)
        }
        view.list_item.adapter = adapter
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.data().subscribe(adapter::swap, ::print)
        )
    }
}