package com.ocs.sequre.app.base

import com.compact.app.CompactActivity

abstract class BaseActivity : CompactActivity() {
    override fun onCreate() {
        supportActionBar?.apply {
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
}
