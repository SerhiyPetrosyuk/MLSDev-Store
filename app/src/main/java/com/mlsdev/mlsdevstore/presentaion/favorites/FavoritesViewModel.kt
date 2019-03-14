package com.mlsdev.mlsdevstore.presentaion.favorites

import androidx.paging.PagedList
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.repository.FavoriteProductsRepository
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import io.reactivex.Observable
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
        private val repository: FavoriteProductsRepository
) : BaseViewModel() {

    val products: Observable<PagedList<Product>> by lazy { repository.getData() }

}