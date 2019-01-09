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
    @Named(NamedAdapter.RANDOM_ITEMS_ADAPTER)
    fun providesRandomItemsAdapter(cart: Cart): ProductsAdapter {
        val adapter = RandomProductsAdapter()
        adapter.setCart(cart)
        return adapter
    }

    @Provides
    @Named(NamedAdapter.SEARCH_RESULT_ITEMS_ADAPTER)
    fun providesSearchResultItemsAdapter(cart: Cart): ProductsAdapter {
        val adapter = ProductsAdapter()
        adapter.setCart(cart)
        return adapter
    }

    @Provides
    @Named(NamedAdapter.CART_ITEMS_ADAPTER)
    fun providesCartItemsAdapter(cart: Cart): ProductsAdapter {
        val adapter = ItemsAdapter()
        adapter.setCart(cart)
        return adapter
    }
}
