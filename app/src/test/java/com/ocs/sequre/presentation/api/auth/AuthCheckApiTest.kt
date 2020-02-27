package com.ocs.sequre.presentation.api.auth

import com.compact.response.ApiException
import com.ocs.sequre.app.CompactTest
import com.ocs.sequre.app.di.TestAppComponent
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.data.remote.api.RequesterAuthApi
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.data.remote.model.response.success.ResponseSuccess
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@RunWith(MockitoJUnitRunner::class)
class AuthCheckApiTest : CompactTest() {

    @Inject
    lateinit var api: RequesterAuthApi
    @Mock
    lateinit var body: AuthValidation

    private val subscriber = TestObserver<ResponseSuccess<AuthModel>>()

    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Test
    fun serverOK() {
        setUpServerOK()
        api.check(body)
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
        api.check(body)
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
            MockDataPathHelper.MOCK_DATA_PATH_REGISTER_CHECK_SUCCESS
        )
    }

    private fun setUpServerBAD() {
        mockHttpResponse(
            HttpsURLConnection.HTTP_BAD_REQUEST,
            MockDataPathHelper.MOCK_DATA_PATH_REGISTER_CHECK_ERROR
        )
    }
}