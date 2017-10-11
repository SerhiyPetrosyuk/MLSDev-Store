package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.presentaion.account.AccountFragment;
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragment;
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentBuilderModule {

    @ContributesAndroidInjector
    StoreFragment contributeStoreFragment();

    @ContributesAndroidInjector
    CartFragment contributeCartFragment();

    @ContributesAndroidInjector
    AccountFragment contributeAccountFragment();

}
