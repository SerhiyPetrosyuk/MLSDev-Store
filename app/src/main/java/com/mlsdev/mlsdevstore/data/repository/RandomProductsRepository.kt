package com.mlsdev.mlsdevstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.remote.datasource.RandomProductsDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.datasource.getPagingConfig
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomProductsRepository @Inject constructor(
        private val dataSourceFactory: RandomProductsDataSourceFactory
) : BaseRepository() {

    fun getItems(): Observable<PagedList<Item>> =
            RxPagedListBuilder(dataSourceFactory, getPagingConfig()).buildObservable()

    fun refresh() {
        dataSourceFactory.invalidateDataSource()
    }

    fun retry() {
        dataSourceFactory.retry()
    }

    fun getPageLoadingState(): LiveData<DataLoadState> =
            Transformations.switchMap(dataSourceFactory.getDataSourceLiveData()) { it.loadStateLiveData }

}