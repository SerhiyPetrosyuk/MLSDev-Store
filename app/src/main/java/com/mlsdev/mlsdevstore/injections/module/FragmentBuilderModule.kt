package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.presentaion.account.AccountFragment
import com.mlsdev.mlsdevstore.presentaion.cart.CartFragment
import com.mlsdev.mlsdevstore.presentaion.store.StoreFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {

    @ContributesAndroidInjector
    fun contributeStoreFragment(): StoreFragment

    @ContributesAndroidInjector
    fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    fun contributeAccountFragment(): AccountFragment

}
