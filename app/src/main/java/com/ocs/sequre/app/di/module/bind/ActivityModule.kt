package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.activity.AuthActivity
import com.ocs.sequre.presentation.ui.activity.HomeActivity
import com.ocs.sequre.presentation.ui.activity.LanguageActivity
import com.ocs.sequre.presentation.ui.activity.LaunchActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun providesLaunchActivity(): LaunchActivity

    @ContributesAndroidInjector
    internal abstract fun providesLanguageActivity(): LanguageActivity

    @ContributesAndroidInjector
    internal abstract fun providesAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    internal abstract fun providesHomeActivity(): HomeActivity
}
