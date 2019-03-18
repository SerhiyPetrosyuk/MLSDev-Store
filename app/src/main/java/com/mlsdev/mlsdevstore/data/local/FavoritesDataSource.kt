package com.mlsdev.mlsdevstore.data.local

import androidx.paging.DataSource
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.remote.datasource.BasePositionDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.datasource.BasePositionalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class FavoritesDataSource @Inject constructor(
        private val database: AppDatabase
) : BasePositionalDataSource<Product>() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Product>) {
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Product>) {
        scope.launch {
            try {
                val products = database.productsDao().queryFavoriteProductsSync()
                callback.onResult(products, 0, database.productsDao().queryFavoritesCount())
            } catch (error: Exception) {
                handleError(error, params, callback)
            }
        }
    }
}

@Singleton
class FavoritesDataSourceFactory @Inject constructor(
        val provider: Provider<FavoritesDataSource>
) : BasePositionDataSourceFactory<Int, Product>() {

    override fun create(): DataSource<Int, Product> {
        val dataSource = provider.get()
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}