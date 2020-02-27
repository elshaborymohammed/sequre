package com.ocs.sequre.app.di

import com.compact.di.module.TestSchedulerModule
import com.ocs.sequre.presentation.api.auth.AuthCheckApiTest
import com.ocs.sequre.presentation.api.auth.AuthLoginApiTest
import com.ocs.sequre.presentation.api.auth.AuthRegisterApiTest
import com.ocs.sequre.presentation.integration.auth.AuthCheckIntegrationTest
import com.ocs.sequre.presentation.integration.auth.AuthLoginIntegrationTest
import com.ocs.sequre.presentation.integration.auth.AuthRegisterIntegrationTest
import com.ocs.sequre.presentation.integration.dependent.DependentCreateIntegrationTest
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
    fun inject(testClass: AuthLoginIntegrationTest)
    fun inject(testClass: AuthCheckIntegrationTest)
    fun inject(testClass: AuthRegisterIntegrationTest)
    fun inject(testClass: DependentCreateIntegrationTest)

    fun inject(testClass: AuthLoginApiTest)
    fun inject(testClass: AuthCheckApiTest)
    fun inject(testClass: AuthRegisterApiTest)
}