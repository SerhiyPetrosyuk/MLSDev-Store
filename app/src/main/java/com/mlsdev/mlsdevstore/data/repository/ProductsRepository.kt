package com.mlsdev.mlsdevstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.remote.ProductsDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.getPagingConfig
import io.reactivex.Observable
import javax.inject.Inject

class ProductsRepository @Inject constructor(
        private val itemsDataSourceFactory: ProductsDataSourceFactory
) {

    fun getItems(categoryId: String): Observable<PagedList<Item>> {
        itemsDataSourceFactory.categoryId = categoryId
        return RxPagedListBuilder(itemsDataSourceFactory, getPagingConfig()).buildObservable()
    }

    fun refresh() {
        itemsDataSourceFactory.refresh()
    }

    fun retry() {
        itemsDataSourceFactory.retry()
    }

    fun getPageLoadingState(): LiveData<DataLoadState> =
            Transformations.switchMap(itemsDataSourceFactory.dataSourceLiveData) { it.loadStateLiveData }

}