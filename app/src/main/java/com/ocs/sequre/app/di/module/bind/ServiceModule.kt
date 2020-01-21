package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.service.CloudMessageService
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ServiceModule {

    @ContributesAndroidInjector
    internal abstract fun providesCloudMessageService(): CloudMessageService
}