package com.mlsdev.mlsdevstore.presentaion.store

import com.mlsdev.mlsdevstore.data.repository.RandomProductsRepository
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class StoreViewModel @Inject
constructor(
        private val appUtils: Utils,
        private val repository: RandomProductsRepository
) : BaseViewModel() {

    val products = repository.getItems()
    val loadingState = repository.getPageLoadingState()

    fun refresh() {
        checkNetworkConnection(appUtils) { repository.refresh() }
    }

    fun retry() {
        checkNetworkConnection(appUtils) { repository.retry() }
    }
}
