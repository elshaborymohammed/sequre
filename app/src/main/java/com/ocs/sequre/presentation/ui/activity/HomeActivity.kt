package com.ocs.sequre.presentation.ui.activity

import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseActivity
import com.ocs.sequre.presentation.preference.AuthPreference
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var auth: AuthPreference

    override fun layoutRes(): Int {
        return R.layout.activity_home
    }
}