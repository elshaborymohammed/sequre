package com.ocs.sequre.presentation.ui.fragment.discount

import android.view.View
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseBottomSheet
import com.ocs.sequre.presentation.preference.AuthPreference
import kotlinx.android.synthetic.main.fragment_auth_sign_out.*
import javax.inject.Inject

class PurchaseSuccessfullyFragment : BaseBottomSheet() {

    @Inject
    lateinit var auth: AuthPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_discount_card_purchase_successfully
    }

    override fun onViewBound(view: View) {
        ok.setOnClickListener { dismissAllowingStateLoss() }
    }
}