package com.ocs.sequre.presentation.ui.activity

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Toast.makeText(this, "new local " + newConfig.locales, Toast.LENGTH_LONG).show()
        }
    }
}