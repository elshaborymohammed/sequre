package com.ocs.sequre.app.di.module

import com.compact.di.module.*
import com.compact.di.qualifier.DatePattern
import com.compact.di.qualifier.Endpoint
import com.compact.requester.adapter.RxCompactCallAdapterFactory
import com.google.gson.Gson
import com.ocs.sequre.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ContextModule::class,
        PreferenceModule::class,
        NetworkModule::class,
        NetworkInterceptorModule::class,
        GsonModule::class,
        RequestBuilderModule::class,
        RequestModule::class,
        SchedulerModule::class,
        GlideModule::class
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
        return BuildConfig.API_BASE_URL
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
}