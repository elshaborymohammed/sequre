package com.ocs.sequre.app.di

import com.compact.di.module.TestSchedulerModule
import com.ocs.sequre.presentation.api.auth.AuthLoginApi
import com.ocs.sequre.presentation.integration.auth.AuthLogin
import dagger.Component
import javax.inject.Singleton

/**
 * this test component uses for injecting dependencies throughout test cases
 */
@Singleton
@Component(
    modules = [
        TestAppModule::class,
        TestSchedulerModule::class
    ]
)
interface TestAppComponent {
    fun inject(testClass: AuthLogin)
    fun inject(testClass: AuthLoginApi)
}