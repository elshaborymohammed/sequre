package com.ocs.sequre.app

import com.ocs.sequre.app.di.TestAppComponent
import com.ocs.sequre.app.helper.MockDataHelper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import javax.inject.Inject

/**
 * this class handel MockServer and MockResponse throughout testing
 */
abstract class CompactTest {

    @Inject
    lateinit var mockServer: MockWebServer

    @Before
    fun setDagger() {
        inject(com.ocs.sequre.app.di.DaggerTestAppComponent.builder().build())
    }

    /**
     * this method used for injecting objects in child class
     */
    abstract fun inject(testAppComponent: TestAppComponent)

    /**
     * used for creating MockResponse
     */
    open fun mockHttpResponse(responseCode: Int, fileName: String) = mockServer.enqueue(
            MockResponse().setResponseCode(responseCode).setBody(MockDataHelper.loadJson(fileName))
    )

    @After
    open fun tearDown() {
        mockServer.shutdown()
    }
}