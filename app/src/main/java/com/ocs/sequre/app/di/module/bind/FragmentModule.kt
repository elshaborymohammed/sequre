package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.fragment.setting.LanguageFragment
import com.ocs.sequre.presentation.ui.activity.SplashFragment
import com.ocs.sequre.presentation.ui.fragment.auth.*
import com.ocs.sequre.presentation.ui.fragment.navigation.HomeFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.MenuFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.NavigationFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.SecondOpinionFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ChangePasswordFragment
import com.ocs.sequre.presentation.ui.fragment.profile.EditProfileFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ProfileFragment
import com.ocs.sequre.presentation.ui.fragment.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class FragmentModule {

    //region Auth Fragments
    @ContributesAndroidInjector
    internal abstract fun providesSplashFragment(): SplashFragment

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
    //endregion

    //region Navigation Fragments
    @ContributesAndroidInjector
    internal abstract fun providesNavigationFragment(): NavigationFragment

    @ContributesAndroidInjector
    internal abstract fun providesHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun providesSecondOpinionFragment(): SecondOpinionFragment
    //endregion

    //region Menu Fragments
    @ContributesAndroidInjector
    internal abstract fun providesMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    internal abstract fun providesProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun providesEditProfileFragment(): EditProfileFragment

    @ContributesAndroidInjector
    internal abstract fun providesSettingFragment(): SettingFragment

    @ContributesAndroidInjector
    internal abstract fun providesChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    internal abstract fun providesLanguageFragment(): LanguageFragment
    //endregion
}