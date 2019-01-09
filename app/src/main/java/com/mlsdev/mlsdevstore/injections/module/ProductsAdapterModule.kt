package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.injections.Named
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter
import com.mlsdev.mlsdevstore.presentaion.store.RandomProductsAdapter

import dagger.Module
import dagger.Provides

@Module
class ProductsAdapterModule {

    companion object {
        const val Default = "default"
        const val RandomProducts = "random_products"
    }

    @Provides
    @Named(Default)
    internal fun productsProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }

    @Provides
    @Named(RandomProducts)
    internal fun providesRandomProductsAdapter(): ProductsAdapter {
        return RandomProductsAdapter()
    }

}
