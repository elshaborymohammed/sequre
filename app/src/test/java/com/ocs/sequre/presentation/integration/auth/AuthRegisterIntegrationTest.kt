package com.ocs.sequre.presentation.integration.auth

import com.compact.executor.RxCompactSchedulers
import com.compact.response.ApiException
import com.ocs.sequre.app.CompactTest
import com.ocs.sequre.app.di.TestAppComponent
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.data.remote.api.RequesterAuthApi
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.domain.entity.Registration
import com.ocs.sequre.presentation.preference.AuthPreference
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@RunWith(MockitoJUnitRunner::class)
class AuthRegisterIntegrationTest : CompactTest() {

    @Inject
    lateinit var api: RequesterAuthApi
    @Inject
    lateinit var compose: RxCompactSchedulers
    @Mock
    lateinit var preference: AuthPreference
    @Mock
    lateinit var body: Registration

    private lateinit var viewModel: AuthViewModel
    private val subscriber = TestObserver<AuthModel>()

    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    override fun setup() {
        viewModel = AuthViewModel(api, preference, compose)
    }

    @Test
    fun serverOK() {
        setUpServerOK()
        viewModel.register(body)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
    }

    @Test
    fun serverBAD() {
        setUpServerBAD()
        viewModel.register(body)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
    }

    @Test
    fun serverERROR() {
        setUpServerError()
        viewModel.register(body)
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

    private fun setUpServerError() {
        mockHttpResponse(HttpsURLConnection.HTTP_INTERNAL_ERROR)
    }
}