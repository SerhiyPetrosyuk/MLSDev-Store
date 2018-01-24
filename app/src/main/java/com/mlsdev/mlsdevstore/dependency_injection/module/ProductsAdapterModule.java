package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.dependency_injection.Named;
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter;
import com.mlsdev.mlsdevstore.presentaion.store.RandomProductsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsAdapterModule {

    public @interface Type {
        String Default = "default";
        String RandomProducts = "random_products";
    }

    @Provides
    @Named(Type.Default)
    ProductsAdapter productsProductsAdapter() {
        return new ProductsAdapter();
    }

    @Provides
    @Named(Type.RandomProducts)
    ProductsAdapter providesRandomProductsAdapter() {
        return new RandomProductsAdapter();
    }

}
