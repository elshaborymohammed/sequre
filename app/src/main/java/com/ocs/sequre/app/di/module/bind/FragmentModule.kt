package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.fragment.LanguageFragment
import com.ocs.sequre.presentation.ui.fragment.SplashFragment
import com.ocs.sequre.presentation.ui.fragment.auth.*
import com.ocs.sequre.presentation.ui.fragment.navigation.BrandFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.CategoryFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.HomeFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ChangePasswordFragment
import com.ocs.sequre.presentation.ui.fragment.profile.EditAddressFragment
import com.ocs.sequre.presentation.ui.fragment.profile.EditProfileFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun providesSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    internal abstract fun providesLanguageFragment(): LanguageFragment

    @ContributesAndroidInjector
    internal abstract fun providesLandingFragment(): LandingFragment

    @ContributesAndroidInjector
    internal abstract fun providesSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    internal abstract fun providesSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    internal abstract fun providesSignOutFragment(): SignOutFragment

    @ContributesAndroidInjector
    internal abstract fun providesVerificationFragment(): VerificationFragment


    @ContributesAndroidInjector
    internal abstract fun providesNavigationFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun providesCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    internal abstract fun providesBrandFragment(): BrandFragment

    @ContributesAndroidInjector
    internal abstract fun providesProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun providesChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    internal abstract fun providesEditAddressFragment(): EditAddressFragment

    @ContributesAndroidInjector
    internal abstract fun providesEditProfileFragment(): EditProfileFragment
}