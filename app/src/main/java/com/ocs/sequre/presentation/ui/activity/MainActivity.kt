package com.ocs.sequre.presentation.ui.activity

import android.os.Bundle
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
    }
}