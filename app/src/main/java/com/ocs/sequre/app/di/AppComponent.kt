package com.ocs.sequre.app.di

import android.app.Application
import com.ocs.sequre.app.App
import com.ocs.sequre.app.di.module.AppModule
import com.ocs.sequre.app.di.module.bind.ActivityModule
import com.ocs.sequre.app.di.module.bind.FragmentModule
import com.ocs.sequre.app.di.module.bind.ServiceModule
import com.ocs.sequre.app.di.module.bind.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        ServiceModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}