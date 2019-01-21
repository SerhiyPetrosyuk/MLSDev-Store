package com.mlsdev.mlsdevstore.data

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.SearchResult

import io.reactivex.Single

interface DataSource {

    /**
     * Default category tree id is needed to get the root [CategoryTree] which contains
     * a list of [CategoryTreeNode]s.
     */
    fun loadDefaultCategoryTreeId(): Single<String>

    /**
     * The root [CategoryTree] contains all main [CategoryTreeNode]s.
     */
    fun loadRootCategoryTree(): Single<CategoryTree>

    /**
     * Deletes all data in the local data storage
     */
    fun refreshRootCategoryTree(): Single<CategoryTree>

    /**
     * @param queries - contains more than a category id.
     * @see [search documentation](https://developer.ebay.com/api-docs/buy/browse/resources/item_summary/methods/search.h2-input)
     */
    fun searchItemsByCategoryId(queries: Map<String, String>): Single<SearchResult>

    fun searchItemsByRandomCategory(): Single<SearchResult>

    fun searchMoreItemsByRandomCategory(): Single<SearchResult>

    fun getItem(itemId: String): Single<Item>

    fun resetSearchResults()
}
