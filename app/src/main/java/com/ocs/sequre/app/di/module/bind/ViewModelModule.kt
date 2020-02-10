package com.ocs.sequre.app.di.module.bind

import androidx.lifecycle.ViewModel
import com.compact.app.viewmodel.di.key.ViewModelKey
import com.compact.app.viewmodel.di.module.ViewModelInjectionModule
import com.ocs.sequre.presentation.viewmodel.AuthViewModel
import com.ocs.sequre.presentation.viewmodel.DataViewModel
import com.ocs.sequre.presentation.viewmodel.ProfileViewModel
import com.ocs.sequre.presentation.viewmodel.UserViewModel
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
    @ViewModelKey(DataViewModel::class)
    internal abstract fun providesDataViewModel(viewModel: DataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun providesProfileViewModel(viewModel: ProfileViewModel): ViewModel
}