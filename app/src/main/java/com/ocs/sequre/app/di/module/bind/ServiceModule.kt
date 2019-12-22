package com.ocs.sequre.app.di.module.bind

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [AndroidInjectionModule::class])
abstract class ServiceModule {

//    @ContributesAndroidInjector
//    internal abstract fun providesTrackerService(): Service
}