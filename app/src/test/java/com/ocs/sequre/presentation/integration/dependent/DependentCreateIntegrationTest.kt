package com.ocs.sequre.presentation.integration.dependent

import com.compact.executor.RxCompactSchedulers
import com.ocs.sequre.app.CompactTest
import com.ocs.sequre.app.di.TestAppComponent
import com.ocs.sequre.app.helper.MockDataPathHelper
import com.ocs.sequre.data.remote.api.RequesterDependentAPI
import com.ocs.sequre.data.remote.model.response.auth.AuthModel
import com.ocs.sequre.domain.entity.Dependent
import com.ocs.sequre.presentation.viewmodel.DependentViewModel
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

@RunWith(MockitoJUnitRunner::class)
class DependentCreateIntegrationTest : CompactTest() {
    @Inject
    lateinit var api: RequesterDependentAPI
    @Inject
    lateinit var compose: RxCompactSchedulers

    @Mock
    lateinit var body: Dependent

    private lateinit var viewModel: DependentViewModel
    private val loading = TestObserver<Boolean>()
    private var subscriber = TestObserver<AuthModel>()

    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    override fun setup() {
        viewModel = DependentViewModel(api, compose)
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
    fun serverOK() {
        setUpServerOK()
        viewModel.create(body).subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
//        subscriber.assertValue(mockTags)

        loading.assertSubscribed()
//        loading.awaitTerminalEvent()
        loading.assertNoErrors()
        loading.assertValues(true, false)
    }

    private fun setUpServerOK() {
        mockHttpResponse(
            HttpsURLConnection.HTTP_OK,
            MockDataPathHelper.MOCK_DATA_PATH_LOGIN_SUCCESS
        )
    }
}