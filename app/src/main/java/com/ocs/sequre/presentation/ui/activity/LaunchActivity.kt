package com.ocs.sequre.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ocs.sequre.R
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.preference.LanguageStatusPreference
import dagger.android.AndroidInjection
import javax.inject.Inject

class LaunchActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: AuthPreference

    @Inject
    lateinit var lang: LanguageStatusPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

//        view.slogan.text =
//            HtmlCompat.fromHtml(
//                getString(R.string.slogan),
//                HtmlCompat.FROM_HTML_MODE_COMPACT
//            )

        Handler().postDelayed({
            if (auth.hasToken()) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                if (lang.hasLang()) {
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, LanguageActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }, 2000L)
    }
}