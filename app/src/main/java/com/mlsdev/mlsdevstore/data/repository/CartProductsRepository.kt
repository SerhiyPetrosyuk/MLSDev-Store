package com.mlsdev.mlsdevstore.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.local.CartItemsDataSourceFactory
import com.mlsdev.mlsdevstore.data.model.item.ListItem
import com.mlsdev.mlsdevstore.data.remote.getPagingConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartProductsRepository @Inject constructor(
        val cart: Cart,
        val dataSourceCartItemsDataSourceFactory: CartItemsDataSourceFactory
) {

    init {
        cart.addOnItemCountChangeListener(object : Cart.OnItemCountChangeListener {
            override fun onItemCountChanged(count: Int) {
                dataSourceCartItemsDataSourceFactory.currentDataSource.value?.invalidate()
            }
        })
    }

    fun getProducts(): LiveData<PagedList<ListItem>> = LivePagedListBuilder(dataSourceCartItemsDataSourceFactory, getPagingConfig()).build()

}