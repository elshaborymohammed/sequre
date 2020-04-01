package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.activity.MainActivity
import com.ocs.sequre.presentation.ui.activity.NotificationActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun providesMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun providesNotificationsActivity(): NotificationActivity
}
