package com.ocs.sequre.presentation.ui.activity

import android.content.Intent
import com.ocs.sequre.R
import com.ocs.sequre.app.base.BaseActivity
import com.ocs.sequre.presentation.preference.LanguageStatusPreference
import kotlinx.android.synthetic.main.fragment_language.*
import javax.inject.Inject

class LanguageActivity : BaseActivity() {

    @Inject
    lateinit var preference: LanguageStatusPreference

    override fun layoutRes(): Int {
        return R.layout.fragment_language
    }

    override fun onCreate() {
        super.onCreate()
        next.setOnClickListener {
            if (preference.set(true)) {
                val intent = Intent(this, AuthActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}