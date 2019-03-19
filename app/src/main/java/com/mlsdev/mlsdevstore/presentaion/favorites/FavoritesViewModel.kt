package com.mlsdev.mlsdevstore.presentaion.favorites

import androidx.lifecycle.LiveData
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

open class FavoritesViewModel @Inject constructor(
        private val dataSource: DataSource
) : BaseViewModel() {

    val favoriteProducts: LiveData<List<Product>> = dataSource.getFavoriteProducts()

    fun addProductIntoFavorites(product: Product) {
        viewModelScope.launch {
            dataSource.addToFavorites(product)
        }
    }

    fun removeProductFromFavorites(product: Product) {
        viewModelScope.launch {
            dataSource.removeFromFavorites(product)
        }
    }

}