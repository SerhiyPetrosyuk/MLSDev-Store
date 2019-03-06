package com.mlsdev.mlsdevstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.remote.datasource.ProductsDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.datasource.getPagingConfig
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ProductsRepository @Inject constructor(
        private val itemsDataSourceFactory: ProductsDataSourceFactory
) : BaseRepository() {

    open fun getItems(categoryId: String): Observable<PagedList<Product>> {
        itemsDataSourceFactory.categoryId = categoryId
        return RxPagedListBuilder(itemsDataSourceFactory, getPagingConfig()).buildObservable()
    }

    open fun refresh() {
        itemsDataSourceFactory.invalidateDataSource()
    }

    open fun retry() {
        itemsDataSourceFactory.retry()
    }

    open fun getPageLoadingState(): LiveData<DataLoadState> =
            Transformations.switchMap(itemsDataSourceFactory.getDataSourceLiveData()) { it.loadStateLiveData }

}