package com.mlsdev.mlsdevstore.data.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.mlsdev.mlsdevstore.data.DataLoadState
import com.mlsdev.mlsdevstore.data.handleLoading
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

const val PAGE_SIZE = 10

fun getPagingConfig(): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

/**
 * Data source
 * */
class ProductsDataSource @Inject constructor(
        private val browseService: BrowseService
) : PositionalDataSource<Item>() {
    private var retryCompletable: Completable? = null
    private var disposable: Disposable? = null
    var categoryId: String = "0"
    val loadStateLiveData = MutableLiveData<DataLoadState>()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item>) {

        try {
            disposable = browseService.searchItemsByCategoryId(categoryId, params.startPosition, params.loadSize + params.startPosition)
                    .handleLoading(loadStateLiveData)
                    .subscribe(
                            { callback.onResult(it.itemSummaries) },
                            { handleError(it, params, callback) })
        } catch (exception: Exception) {
            handleError(exception, params, callback)
        }

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item>) {

        try {
            loadStateLiveData.postValue(DataLoadState.LOADING)
            disposable = browseService.searchItemsByCategoryId(categoryId, params.pageSize, 0)
                    .handleLoading(loadStateLiveData)
                    .subscribe(
                            { callback.onResult(it.itemSummaries, it.offset, it.total) },
                            { handleError(it, params, callback) })
        } catch (exception: Exception) {
            handleError(exception, params, callback)
        }

    }

    @SuppressLint("CheckResult")
    fun retry() {

        retryCompletable?.let {
            setRetry(null)
            it.observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Log.d("debugDS", "error: " + throwable.message) })
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    private fun handleError(exp: Throwable, params: LoadInitialParams, callback: LoadInitialCallback<Item>) {
        // keep a Completable for future retry
        setRetry(Action { loadInitial(params, callback) })
        // publish the error
        loadStateLiveData.postValue(DataLoadState.FAILED)
        exp.printStackTrace()
    }

    private fun handleError(exp: Throwable, params: LoadRangeParams, callback: LoadRangeCallback<Item>) {
        // keep a Completable for future retry
        setRetry(Action { loadRange(params, callback) })
        // publish the error
        loadStateLiveData.postValue(DataLoadState.FAILED)
        exp.printStackTrace()
    }

}

/**
 * Data source factory
 * */
@Singleton
class ProductsDataSourceFactory @Inject constructor(
        val provider: Provider<ProductsDataSource>
) : DataSource.Factory<Int, Item>() {

    var categoryId: String = "0"
    val dataSourceLiveData = MutableLiveData<ProductsDataSource>()

    override fun create(): DataSource<Int, Item> {
        val productsDataSource = provider.get()
        productsDataSource.categoryId = categoryId
        dataSourceLiveData.postValue(productsDataSource)
        return productsDataSource
    }

    fun refresh() {
        dataSourceLiveData.value?.invalidate()
    }

    fun retry() {
        dataSourceLiveData.value?.retry()
    }

}