package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.injections.component.scope.ActivityScope
import com.mlsdev.mlsdevstore.presentaion.account.EditPersonalInfoActivity
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesActivity
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity
import com.mlsdev.mlsdevstore.presentaion.splashscreen.SplashScreenActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuilderModule::class))
    @ActivityScope
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeCategoriesActivity(): CategoriesActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeProductDetailsActivity(): ProductDetailsActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeCheckoutActivity(): CheckoutActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun contributeEditPersonalInfoActivity(): EditPersonalInfoActivity

}