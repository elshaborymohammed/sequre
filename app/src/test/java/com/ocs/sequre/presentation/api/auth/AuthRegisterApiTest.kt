package com.ocs.sequre.presentation.api.auth

import com.compact.response.ApiException
import com.ocs.sequre.app.CompactTest
import com.ocs.sequre.app.di.TestAppComponent
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.data.remote.api.RequesterAuthApi
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import com.ocs.sequre.domain.entity.Registration
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@RunWith(MockitoJUnitRunner::class)
class AuthRegisterApiTest : CompactTest() {

    @Inject
    lateinit var api: RequesterAuthApi

    private val subscriber = TestObserver<ResponseSuccess<AuthModel>>()

    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Test
    fun serverOK() {
        setUpServerOK()
        api.register(Registration("", "", "", "", ""))
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
//        subscriber.assertValue(mockTags)
    }

    @Test
    fun serverBAD() {
        setUpServerBAD()
        api.register(Registration("", "", "", "", ""))
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
    }

    private fun setUpServerOK() {
        mockHttpResponse(
            HttpsURLConnection.HTTP_OK,
            MockDataPathHelper.MOCK_DATA_PATH_REGISTER_SUCCESS
        )
    }

    private fun setUpServerBAD() {
        mockHttpResponse(
            HttpsURLConnection.HTTP_BAD_REQUEST,
            MockDataPathHelper.MOCK_DATA_PATH_REGISTER_ERROR
        )
    }
}