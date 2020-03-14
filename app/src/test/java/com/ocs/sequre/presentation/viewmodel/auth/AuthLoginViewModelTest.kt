package com.ocs.sequre.presentation.viewmodel.auth

import com.compact.response.ApiException
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import com.ocs.sequre.app.helper.MockDataHelper.fromJson
import com.ocs.sequre.app.helper.MockDataHelper.loadJson
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.domain.entity.AuthModel
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class AuthLoginViewModelTest {

    @Mock
    lateinit var viewModel: AuthViewModel

    private val loading: Relay<Boolean> = BehaviorRelay.create()
    private var subscriber = TestObserver<AuthModel>()

    @Before
    fun setUp() {
        Mockito.`when`(viewModel.loading()).thenReturn(loading)
    }

    @Test
    fun loading() {
        val test = viewModel.loading().test()
        viewModel.loading().accept(true)
        viewModel.loading().accept(false)

        test.assertSubscribed()
        test.assertValues(true, false)
    }

    @Test
    fun success() {
        val mock = setUpSuccess()
        viewModel.login("", "").subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun errorAPI() {
        val mock = setUpAPIError()
        viewModel.login("", "").subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
        subscriber.assertError(mock)
    }

    @Test
    fun errorIO() {
        val mock = setUpIOError()
        viewModel.login("", "").subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(IOException::class.java)
        subscriber.assertError(mock)
    }

    private fun setUpSuccess(): AuthModel {
        val response: AuthModel = fromJson(MockDataPathHelper.MOCK_DATA_PATH_LOGIN_SUCCESS)
        Mockito.`when`(viewModel.login("", "")).thenReturn(Single.just(response))
        return response
    }

    private fun setUpAPIError(): Throwable {
        val error = ApiException(400, loadJson(MockDataPathHelper.MOCK_DATA_PATH_LOGIN_ERROR))
        Mockito.`when`(viewModel.login("", "")).thenReturn(Single.error(error))
        return error
    }

    private fun setUpIOError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.login("", "")).thenReturn(Single.error(error))
        return error
    }
}