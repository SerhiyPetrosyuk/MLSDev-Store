package com.mlsdev.mlsdevstore.data.remote.datasource

import androidx.paging.DataSource
import com.mlsdev.mlsdevstore.data.handleLoading
import com.mlsdev.mlsdevstore.data.local.Key
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider

// Data source
class RandomProductsDataSource @Inject constructor(
        private val database: AppDatabase,
        private val browseService: BrowseService,
        private val sharedPreferencesManager: SharedPreferencesManager
) : BasePositionalDataSource<Item>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item>) {
        try {
            if ((params.startPosition + params.loadSize) >= totalCount) {
                callback.onResult(emptyList())
                return
            }

            val categoryId = sharedPreferencesManager[Key.RANDOM_CATEGORY_TREE_NODE, CategoryTreeNode::class.java]
                    ?.category?.categoryId ?: "0"

            disposable = browseService.searchItemsByCategoryId(categoryId, params.loadSize, params.startPosition)
                    .handleLoading(loadStateLiveData)
                    .subscribe(
                            { callback.onResult(it.itemSummaries) },
                            { handleError(it, params, callback) })

        } catch (e: Exception) {
            handleError(e, params, callback)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item>) {
        try {
            disposable = Single.just(listOf(sharedPreferencesManager[Key.RANDOM_CATEGORY_TREE_NODE, CategoryTreeNode::class.java]))
                    .flatMap {
                        if (it.isEmpty()) return@flatMap database.categoriesDao().queryCategoryTreeNode()
                        else return@flatMap Single.just(it)
                    }
                    .toFlowable()
                    .flatMap { Flowable.fromIterable(it) }
                    .filter { node -> node.category != null && node.category.categoryId != null }
                    .toList()
                    .map { nodes -> nodes[(Math.random() * nodes.size).toInt()] }
                    .flatMap { node ->
                        sharedPreferencesManager.save(Key.RANDOM_CATEGORY_TREE_NODE, node)
                        return@flatMap browseService.searchItemsByCategoryId(node.category.categoryId, params.pageSize, 0)
                                .handleLoading(loadStateLiveData)
                    }
                    .subscribe(
                            {
                                totalCount = it.total
                                callback.onResult(it.itemSummaries, it.offset, it.total)
                            },
                            { handleError(it, params, callback) })
        } catch (e: Exception) {
            handleError(e, params, callback)
        }
    }

}

// Data source factory
class RandomProductsDataSourceFactory @Inject constructor(
        val provider: Provider<RandomProductsDataSource>
) : BasePositionDataSourceFactory<Int, Item>() {
    override fun create(): DataSource<Int, Item> {
        val dataSource = provider.get()
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}