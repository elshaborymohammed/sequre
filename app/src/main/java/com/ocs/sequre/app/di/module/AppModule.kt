package com.ocs.sequre.app.di.module

import android.content.Context
import android.content.Intent
import com.compact.di.module.*
import com.compact.di.qualifier.ApplicationContext
import com.compact.di.qualifier.DatePattern
import com.compact.di.qualifier.Endpoint
import com.compact.requester.adapter.RxCompactCallAdapterFactory
import com.google.gson.Gson
import com.ocs.sequre.BuildConfig
import com.ocs.sequre.presentation.ui.activity.MainActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ContextModule::class,
        PreferenceModule::class,
        NetworkModule::class,
        GsonModule::class,
        RequestModule::class,
        SchedulerModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    @DatePattern
    fun providesDatePattern(): String {
        return GsonModule.providesDatePattern()
    }

    @Provides
    @Singleton
    @Endpoint
    fun providesEndpoint(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @IntoSet
    internal fun providesGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @IntoSet
    fun providesCompactCallAdapterFactory(): CallAdapter.Factory {
        return RxCompactCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesAuthenticator(@ApplicationContext context: Context): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                return response.request
            }

        }
    }
}