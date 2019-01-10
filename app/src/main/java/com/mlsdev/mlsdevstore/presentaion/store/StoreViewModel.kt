package com.mlsdev.mlsdevstore.presentaion.store

import androidx.lifecycle.MutableLiveData
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.item.SearchResult
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

class StoreViewModel @Inject
constructor(dataSource: DataSource, utils: Utils) : BaseViewModel() {
    val searchResultLiveData = MutableLiveData<SearchResult>()

    private val searchResultsConsumer = Consumer<SearchResult> { searchResult ->
        isLoading.set(false)
        isRefreshing.set(false)
        searchResultLiveData.postValue(searchResult)
    }

    init {
        this.dataSource = dataSource
        this.utils = utils
    }

    fun getProducts() {
        checkNetworkConnection(utils!!) {
            isLoading.set(true)
            isLoading.notifyChange()
            compositeDisposable.add(dataSource!!.searchItemsByRandomCategory()
                    .subscribe(searchResultsConsumer, errorConsumer))
        }
    }

    fun refresh() {
        checkNetworkConnection(utils!!) {
            isRefreshing.set(true)
            dataSource!!.resetSearchResults()
            compositeDisposable.add(dataSource!!.searchItemsByRandomCategory()
                    .subscribe(searchResultsConsumer, errorConsumer))
        }
    }

    fun loadMoreItemsFromRandomCategory() {
        checkNetworkConnection(utils!!) {
            compositeDisposable.add(dataSource!!.searchMoreItemsByRandomCategory().subscribe(
                    { result ->
                        isLoading.set(false)
                        isRefreshing.set(false)
                        searchResultLiveData.value?.let { liveSearchResult ->
                            liveSearchResult.offset = result.offset
                            liveSearchResult.itemSummaries.addAll(result.itemSummaries)
                            searchResultLiveData.postValue(searchResultLiveData.value)
                        }
                    },
                    { handleError(it) }))
        }
    }
}
