package com.mlsdev.mlsdevstore.data.remote

import android.util.ArrayMap
import android.util.Base64

import com.mlsdev.mlsdevstore.BuildConfig
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessTokenRequestBody
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.item.CategoryDistribution
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.Refinement
import com.mlsdev.mlsdevstore.data.model.item.SearchResult
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService

import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key

class RemoteDataSource(private val browseService: BrowseService,
                       private val authenticationService: AuthenticationService,
                       private val taxonomyService: TaxonomyService,
                       private val sharedPreferencesManager: SharedPreferencesManager,
                       private val database: AppDatabase) : DataSource {
    private var searchOffset = 0
    private var searchLimit = 10
    private val searchItems = ArrayList<Item>()

    val appAccessToken: Single<AppAccessToken>
        get() {
            val originalOAuthCredentials = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET
            var oAuthCredentialsBytes = ByteArray(0)
            try {
                oAuthCredentialsBytes = originalOAuthCredentials.toByteArray(charset("UTF-8"))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            val encodedOAuthCredentials = Base64.encodeToString(oAuthCredentialsBytes, Base64.NO_WRAP)
            val headers = HashMap<String, String>(2)
            headers["Content-Type"] = "application/x-www-form-urlencoded"
            headers["Authorization"] = "Basic $encodedOAuthCredentials"
            val body = AppAccessTokenRequestBody()

            return prepareSingle(authenticationService.getAppAccessToken(headers, body.fields))
                    .map { appAccessToken ->
                        val currentTime = Calendar.getInstance().timeInMillis
                        val expirationDate = currentTime + appAccessToken.expiresIn!!
                        appAccessToken.expirationDate = expirationDate
                        appAccessToken
                    }
                    .doOnSuccess { appAccessToken -> sharedPreferencesManager.save(Key.APPLICATION_ACCESS_TOKEN, appAccessToken) }
        }

    private val searchResultConsumer = { searchResult: SearchResult ->
        searchItems.addAll(searchResult.itemSummaries)
        searchLimit = searchResult.limit
        searchOffset = searchItems.size
        searchResult.refinement = categoryRefinement
    }

    private val categoryRefinement: Refinement
        get() {
            val node = sharedPreferencesManager[Key.RANDOM_CATEGORY_TREE_NODE, CategoryTreeNode::class.java]
            val refinement = Refinement()
            val distributionList = ArrayList<CategoryDistribution>(1)
            val distribution = CategoryDistribution()
            distribution.categoryId = node!!.category.categoryId
            distribution.categoryName = node.category.categoryName
            distributionList.add(distribution)
            refinement.categoryDistributions = distributionList
            return refinement
        }

    override fun loadDefaultCategoryTreeId(): Single<String> {
        return prepareSingle(taxonomyService.defaultCategoryTreeId)
                .map { it.categoryTreeId }
                .doOnSuccess { this.saveDefaultCategoryTreeId(it) }
    }

    override fun loadRootCategoryTree(): Single<CategoryTree> {
        return loadDefaultCategoryTreeId()
                .flatMap { defaultCategoryTreeId -> prepareSingle(taxonomyService.getCategoryTree(defaultCategoryTreeId)) }
                .doOnSuccess { this.saveCategoryTreeNodes(it) }
    }

    override fun refreshRootCategoryTree(): Single<CategoryTree> {
        return loadRootCategoryTree()
    }

    override fun searchItemsByCategoryId(queries: Map<String, String>): Single<SearchResult> {
        return prepareSingle(browseService.searchItemsByCategoryId(queries))
    }

    override fun searchItemsByRandomCategory(): Single<SearchResult> {
        if (!searchItems.isEmpty()) {
            val searchResult = SearchResult()
            searchResult.itemSummaries = searchItems
            searchResult.refinement = categoryRefinement
            return Single.just(searchResult)
        }

        return prepareSingle(database.categoriesDao().queryCategoryTreeNode())
                .toFlowable()
                .flatMap { Flowable.fromIterable(it) }
                .filter { node -> node.category != null && node.category.categoryId != null }
                .toList()
                .map { nodes -> nodes[(Math.random() * nodes.size).toInt()] }
                .flatMap { node ->
                    sharedPreferencesManager.save(Key.RANDOM_CATEGORY_TREE_NODE, node)
                    val queries = prepareSearchQueryMap()
                    prepareSingle(browseService.searchItemsByCategoryId(queries))
                }
                .flatMap { searchResult -> if (searchResult.itemSummaries.isEmpty()) searchItemsByRandomCategory() else Single.just(searchResult) }
                .doOnSuccess(searchResultConsumer)
    }

    override fun searchMoreItemsByRandomCategory(): Single<SearchResult> {
        return prepareSingle(browseService.searchItemsByCategoryId(prepareSearchQueryMap()))
                .doOnSuccess(searchResultConsumer)
    }

    override fun getItem(itemId: String): Single<Item> {
        return prepareSingle(browseService.getItem(itemId))
    }

    override fun resetSearchResults() {
        searchOffset = 0
        searchItems.clear()
        sharedPreferencesManager.remove(Key.RANDOM_CATEGORY_TREE_NODE)
    }

    private fun prepareSearchQueryMap(): Map<String, String> {
        val node = sharedPreferencesManager[Key.RANDOM_CATEGORY_TREE_NODE, CategoryTreeNode::class.java]
        val queries = ArrayMap<String, String>()
        queries["category_ids"] = node!!.category.categoryId
        queries["limit"] = searchLimit.toString()
        queries["offset"] = searchItems.size.toString()
        return queries
    }

    fun <T> prepareSingle(single: Single<T>): Single<T> {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun saveCategoryTreeNodes(categoryTree: CategoryTree) {
        Completable.create { e -> database.categoriesDao().insertCategoryTreeNode(categoryTree.categoryTreeNode.childCategoryTreeNodes) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun saveDefaultCategoryTreeId(categoryTreeId: String) {
        val categoryTree = CategoryTree()
        categoryTree.categoryTreeId = categoryTreeId
        Completable.create { e -> database.categoriesDao().insertCategoryTree(categoryTree) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}