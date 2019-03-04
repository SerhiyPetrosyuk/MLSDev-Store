package com.mlsdev.mlsdevstore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.local.Key
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.remote.datasource.RandomProductsDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.datasource.getPagingConfig
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomProductsRepository @Inject constructor(
        private val database: AppDatabase,
        private val prefsManager: SharedPreferencesManager,
        private val dataSourceFactory: RandomProductsDataSourceFactory
) : BaseRepository() {

    fun getItems(): Observable<PagedList<Product>> =
            RxPagedListBuilder(dataSourceFactory, getPagingConfig()).buildObservable()

    fun refresh() {
        Single.fromCallable {
            prefsManager.remove(Key.RANDOM_CATEGORY_TREE_NODE)
            database.productsDao().deleteAllProducts()
            dataSourceFactory.invalidateDataSource()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun retry() {
        dataSourceFactory.retry()
    }

    fun getPageLoadingState(): LiveData<DataLoadState> =
            Transformations.switchMap(dataSourceFactory.getDataSourceLiveData()) { it.loadStateLiveData }

}