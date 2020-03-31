package com.ocs.sequre.presentation.ui.activity

import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseActivity

class NotificationActivity : BaseActivity() {

    override fun layoutRes() = R.layout.activity_notifications

    override fun onBackPressed() {
        super.onBackPressed()
        MainActivity.start(this)
        finishAffinity()
    }
}