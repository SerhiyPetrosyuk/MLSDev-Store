package com.mlsdev.mlsdevstore.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mlsdev.mlsdevstore.data.local.FavoritesDataSourceFactory
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.remote.datasource.getPagingConfig
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class FavoriteProductsRepository @Inject constructor(
        private val dataSourceFactory: FavoritesDataSourceFactory
) : Repository<Product> {

    override fun getData(): Observable<PagedList<Product>> =
            RxPagedListBuilder<Int, Product>(dataSourceFactory, getPagingConfig()).buildObservable()

}