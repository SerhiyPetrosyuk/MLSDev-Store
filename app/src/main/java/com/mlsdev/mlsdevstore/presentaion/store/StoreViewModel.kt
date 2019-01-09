package com.mlsdev.mlsdevstore.presentaion.store

import androidx.databinding.ObservableField

import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.item.SearchResult
import com.mlsdev.mlsdevstore.data.remote.BaseObserver
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel

import javax.inject.Inject

class StoreViewModel @Inject
constructor(dataSource: DataSource, utils: Utils) : BaseViewModel() {
    val searchResult = ObservableField<SearchResult>()

    private val searchResultBaseObserver = object : BaseObserver<SearchResult>(this) {
        override fun onSuccess(searchResult: SearchResult) {
            super.onSuccess(searchResult)
            this@StoreViewModel.searchResult.set(searchResult)
            this@StoreViewModel.searchResult.notifyChange()
        }
    }

    init {
        this.dataSource = dataSource
        this.utils = utils
    }

    fun getProducts() {
        if (!utils!!.isNetworkAvailable) {
            onNetworkErrorOccurred()
            return
        }

        isLoading.set(true)
        isLoading.notifyChange()
        dataSource!!.searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver)
    }

    fun refresh() {
        if (!utils!!.isNetworkAvailable) {
            onNetworkErrorOccurred()
            return
        }

        isRefreshing.set(true)
        dataSource!!.resetSearchResults()
        dataSource!!.searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver)
    }

    fun loadMoreItemsFromRandomCategory() {
        if (!utils!!.isNetworkAvailable) {
            onNetworkErrorOccurred()
            return
        }

        dataSource!!.searchMoreItemsByRandomCategory()
                .subscribe(object : BaseObserver<SearchResult>(this) {
                    override fun onSuccess(data: SearchResult) {
                        super.onSuccess(data)
                        searchResult.get()!!.offset = data.offset
                        searchResult.get()!!.itemSummaries.addAll(data.itemSummaries)
                        searchResult.notifyChange()
                    }
                })
    }
}
