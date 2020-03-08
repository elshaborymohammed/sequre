package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.activity.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun providesMainActivity(): MainActivity
}
