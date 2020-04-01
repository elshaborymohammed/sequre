package com.ocs.sequre.presentation.ui.activity

import android.app.Activity
import android.content.Intent
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

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }
}