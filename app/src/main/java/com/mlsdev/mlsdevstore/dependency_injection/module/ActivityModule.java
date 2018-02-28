package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesActivity;
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity;
import com.mlsdev.mlsdevstore.presentaion.splashscreen.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    SplashScreenActivity contributeSplashScreenActivity();

    @ContributesAndroidInjector
    CategoriesActivity contributeCategoriesActivity();

    @ContributesAndroidInjector
    ProductDetailsActivity contributeProductDetailsActivity();

    @ContributesAndroidInjector
    CheckoutActivity contributeCheckoutActivity();

}
