package com.mlsdev.mlsdevstore.data.local

import androidx.paging.DataSource
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.remote.datasource.BasePositionDataSourceFactory
import com.mlsdev.mlsdevstore.data.remote.datasource.BasePositionalDataSource
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class FavoritesDataSource @Inject constructor(
        private val database: AppDatabase
) : BasePositionalDataSource<Product>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Product>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Product>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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