package com.ocs.sequre.app.di.module.bind

import com.ocs.sequre.presentation.ui.activity.LaunchFragment
import com.ocs.sequre.presentation.ui.fragment.auth.*
import com.ocs.sequre.presentation.ui.fragment.discount.DiscountCardSummaryFragment
import com.ocs.sequre.presentation.ui.fragment.discount.DiscountCardsFragment
import com.ocs.sequre.presentation.ui.fragment.discount.OfferFragment
import com.ocs.sequre.presentation.ui.fragment.discount.PurchaseSuccessfullyFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.HomeFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.MenuFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.NavigationFragment
import com.ocs.sequre.presentation.ui.fragment.navigation.SecondOpinionFragment
import com.ocs.sequre.presentation.ui.fragment.profile.AddressFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ChangePasswordFragment
import com.ocs.sequre.presentation.ui.fragment.profile.EditProfileFragment
import com.ocs.sequre.presentation.ui.fragment.profile.ProfileFragment
import com.ocs.sequre.presentation.ui.fragment.profile.dependent.DependentCreateFragment
import com.ocs.sequre.presentation.ui.fragment.profile.dependent.DependentUpdateFragment
import com.ocs.sequre.presentation.ui.fragment.profile.dependent.DependentsFragment
import com.ocs.sequre.presentation.ui.fragment.provider.MapsFragment
import com.ocs.sequre.presentation.ui.fragment.provider.ServiceProviderFragment
import com.ocs.sequre.presentation.ui.fragment.provider.ServiceProvidersFragment
import com.ocs.sequre.presentation.ui.fragment.setting.LanguageFragment
import com.ocs.sequre.presentation.ui.fragment.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class FragmentModule {

    //region Auth Fragments
    @ContributesAndroidInjector
    internal abstract fun providesSplashFragment(): LaunchFragment

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

    //region Side Menu Fragments
    @ContributesAndroidInjector
    internal abstract fun providesMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    internal abstract fun providesSettingFragment(): SettingFragment

    @ContributesAndroidInjector
    internal abstract fun providesChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    internal abstract fun providesLanguageFragment(): LanguageFragment
    //endregion

    //region Profile Fragments
    @ContributesAndroidInjector
    internal abstract fun providesProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun providesEditProfileFragment(): EditProfileFragment

    @ContributesAndroidInjector
    internal abstract fun providesAddressFragment(): AddressFragment

    @ContributesAndroidInjector
    internal abstract fun providesDependentsFragment(): DependentsFragment

    @ContributesAndroidInjector
    internal abstract fun providesDependentCreateFragment(): DependentCreateFragment

    @ContributesAndroidInjector
    internal abstract fun providesDependentUpdateFragment(): DependentUpdateFragment
    //endregion

    //region Discount Cards Fragments
    @ContributesAndroidInjector
    internal abstract fun providesDiscountCardsFragment(): DiscountCardsFragment

    @ContributesAndroidInjector
    internal abstract fun providesDiscountCardSummaryFragment(): DiscountCardSummaryFragment

    @ContributesAndroidInjector
    internal abstract fun providesOfferFragment(): OfferFragment

    @ContributesAndroidInjector
    internal abstract fun providesPurchaseSuccessfully(): PurchaseSuccessfullyFragment
    //endregion

    //region Service Provider Fragments
    @ContributesAndroidInjector
    internal abstract fun providesServiceProviderFragment(): ServiceProviderFragment

    @ContributesAndroidInjector
    internal abstract fun providesMapsFragment(): MapsFragment

    @ContributesAndroidInjector
    internal abstract fun providesServiceProvidersFragment(): ServiceProvidersFragment
    //endregion
}