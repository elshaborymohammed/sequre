package com.ocs.sequre.presentation.viewmodel.auth

import com.compact.response.ApiException
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import com.ocs.sequre.app.helper.MockDataHelper.loadJson
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.data.remote.model.request.auth.AuthValidation
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class AuthCheckTest {

    @Mock
    lateinit var viewModel: AuthViewModel
    @Mock
    lateinit var body: AuthValidation

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
        setUpSuccess()
        viewModel.check(body).subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertComplete()
    }

    @Test
    fun errorAPI() {
        val mock = setUpAPIError()
        viewModel.check(body).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
        subscriber.assertError(mock)
    }

    @Test
    fun errorServer() {
        val mock = setUpServerError()
        viewModel.check(body).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
        subscriber.assertError(mock)
    }

    @Test
    fun errorIO() {
        val mock = setUpIOError()
        viewModel.check(body).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(IOException::class.java)
        subscriber.assertError(mock)
    }

    private fun setUpSuccess() {
        Mockito.`when`(viewModel.check(body)).thenReturn(Completable.complete())
    }

    private fun setUpAPIError(): Throwable {
        val error = ApiException(400, loadJson(MockDataPathHelper.MOCK_DATA_PATH_LOGIN_ERROR))
        Mockito.`when`(viewModel.check(body)).thenReturn(Completable.error(error))
        return error
    }

    private fun setUpServerError(): Throwable {
        val error = ApiException(500, null)
        Mockito.`when`(viewModel.check(body)).thenReturn(Completable.error(error))
        return error
    }

    private fun setUpIOError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.check(body)).thenReturn(Completable.error(error))
        return error
    }
}