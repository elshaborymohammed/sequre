package com.ocs.sequre.app.di.module

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides


@Module
class GlideModule {

    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions().transform(CenterCrop(), RoundedCorners(20))
    }
}