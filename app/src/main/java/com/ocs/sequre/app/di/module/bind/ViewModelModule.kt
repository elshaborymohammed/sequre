package com.ocs.sequre.app.di.module.bind

import androidx.lifecycle.ViewModel
import com.compact.app.viewmodel.di.key.ViewModelKey
import com.compact.app.viewmodel.di.module.ViewModelInjectionModule
import com.ocs.sequre.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelInjectionModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun providesUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun providesProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondOpinionViewModel::class)
    internal abstract fun providesSecondOpinionViewModel(viewModel: SecondOpinionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DependentViewModel::class)
    internal abstract fun providesDependentViewModel(viewModel: DependentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscountCardViewModel::class)
    internal abstract fun providesDiscountCardViewModel(viewModel: DiscountCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    internal abstract fun provideNotificationsViewModel(viewModel: NotificationsViewModel): ViewModel
}