package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.injections.component.scope.ActivityScope
import com.mlsdev.mlsdevstore.presentaion.AppActivity
import com.mlsdev.mlsdevstore.presentaion.account.EditPersonalInfoActivity
import com.mlsdev.mlsdevstore.presentaion.account.EditShippingInfoActivity
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity
import com.mlsdev.mlsdevstore.presentaion.splashscreen.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    @ActivityScope
    fun contributeAppActivity(): AppActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeCheckoutActivity(): CheckoutActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeEditPersonalInfoActivity(): EditPersonalInfoActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeEditShippingInfoActivity(): EditShippingInfoActivity

}
