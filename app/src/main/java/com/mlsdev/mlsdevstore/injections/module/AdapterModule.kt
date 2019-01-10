package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.injections.NamedAdapter
import com.mlsdev.mlsdevstore.presentaion.cart.ItemsAdapter
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter
import com.mlsdev.mlsdevstore.presentaion.store.RandomProductsAdapter

import javax.inject.Named

import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun providesRandomItemsAdapter(cart: Cart): RandomProductsAdapter {
        val adapter = RandomProductsAdapter()
        adapter.cart = cart
        return adapter
    }

    @Provides
    fun providesSearchResultItemsAdapter(cart: Cart): ProductsAdapter {
        val adapter = ProductsAdapter()
        adapter.cart = cart
        return adapter
    }

    @Provides
    fun providesCartItemsAdapter(cart: Cart): ItemsAdapter {
        val adapter = ItemsAdapter()
        adapter.cart = cart
        return adapter
    }
}
