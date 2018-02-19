package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.dependency_injection.NamedAdapter;
import com.mlsdev.mlsdevstore.presentaion.cart.ItemsAdapter;
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter;
import com.mlsdev.mlsdevstore.presentaion.store.RandomProductsAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @Provides
    @Named(NamedAdapter.RANDOM_ITEMS_ADAPTER)
    ProductsAdapter providesRandomItemsAdapter(Cart cart) {
        ProductsAdapter adapter = new RandomProductsAdapter();
        adapter.setCart(cart);
        return adapter;
    }

    @Provides
    @Named(NamedAdapter.SEARCH_RESULT_ITEMS_ADAPTER)
    ProductsAdapter providesSearchResultItemsAdapter(Cart cart) {
        ProductsAdapter adapter = new ProductsAdapter();
        adapter.setCart(cart);
        return adapter;
    }

    @Provides
    @Named(NamedAdapter.CART_ITEMS_ADAPTER)
    ProductsAdapter providesCartItemsAdapter(Cart cart) {
        ProductsAdapter adapter = new ItemsAdapter();
        adapter.setCart(cart);
        return adapter;
    }
}
