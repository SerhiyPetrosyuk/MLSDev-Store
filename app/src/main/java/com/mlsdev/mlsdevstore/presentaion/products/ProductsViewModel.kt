package com.mlsdev.mlsdevstore.presentaion.products

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.paging.PagedList
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.repository.ProductsRepository
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
        private val appUtils: Utils,
        private val productsRepository: ProductsRepository
) : BaseViewModel() {

    lateinit var products: Observable<PagedList<Item>>
    val categoryName = ObservableField<String>("")

    fun initCategoryData(categoryData: Bundle?) {
        checkNetworkConnection(appUtils) {
            val categoryId = categoryData?.getString(ExtrasKeys.KEY_CATEGORY_ID)
            categoryName.set(categoryData?.getString(ExtrasKeys.KEY_CATEGORY_NAME, "") ?: "")

            if (categoryId != null) products = productsRepository.getItems(categoryId)
            else throw Exception("'Category id' hasn't been passed")
        }
    }

    fun refresh() {
        checkNetworkConnection(appUtils) { productsRepository.refresh() }
    }

    fun retry() {
        checkNetworkConnection(appUtils) { productsRepository.retry() }
    }

}