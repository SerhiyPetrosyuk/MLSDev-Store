package com.mlsdev.mlsdevstore.data.local

import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.model.product.SearchResult
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo
import com.mlsdev.mlsdevstore.data.remote.datasource.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class LocalDataSource(
        private val remoteDataSource: RemoteDataSource,
        private val database: AppDatabase
) : DataSource {

    val personalInfo: Single<PersonalInfo>
        get() = remoteDataSource
                .prepareSingle(database.personalInfoDao().queryPersonalInfo())
                .map { personalInfoList -> if (!personalInfoList.isEmpty()) personalInfoList[0] else PersonalInfo() }

    fun getShippingInfo(): Single<Address> = remoteDataSource
            .prepareSingle(database.addressDao().queryByType(Address.Type.SHIPPING))
            .map { addresses -> if (!addresses.isEmpty()) addresses[0] else Address() }

    override fun loadDefaultCategoryTreeId(): Single<String> {
        val single = database.categoriesDao().queryDefaultCategoryTree()
                .flatMap { list ->
                    if (!list.isEmpty())
                        Single.just(list[0].categoryTreeId)
                    else
                        remoteDataSource.loadDefaultCategoryTreeId()
                }
        return remoteDataSource.prepareSingle(single)
    }

    override fun loadRootCategoryTree(): Single<CategoryTree> {
        val listSingle = database.categoriesDao().queryCategoryTreeNode()
        return remoteDataSource.prepareSingle(listSingle)
                .flatMap { nodes ->
                    val categoryTree = CategoryTree()
                    val categoryTreeNode = CategoryTreeNode()
                    categoryTree.categoryTreeNode = categoryTreeNode
                    categoryTreeNode.childCategoryTreeNodes = nodes

                    if (!nodes.isEmpty())
                        Single.just(categoryTree)
                    else
                        remoteDataSource.loadRootCategoryTree()
                }
    }

    override fun refreshRootCategoryTree(): Single<CategoryTree> {

        return Single.fromCallable {
            database.categoriesDao().deleteAllCategoryTreeNodes()
            database.categoriesDao().deleteAllCategoryTrees()
            1
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { remoteDataSource.refreshRootCategoryTree() }
    }

    override fun searchItemsByCategoryId(queries: Map<String, String>): Single<SearchResult> {
        return remoteDataSource.searchItemsByCategoryId(queries)
    }

    override fun searchMoreItemsByRandomCategory(): Single<SearchResult> {
        return remoteDataSource.searchMoreItemsByRandomCategory()
    }

    override fun getItem(itemId: String): Single<Product> {
        return remoteDataSource.getItem(itemId)
    }

    override fun resetSearchResults() {
        remoteDataSource.resetSearchResults()
    }

    fun updatePersonalInfo(email: String?, firstName: String?, lastName: String?): Completable {
        val personalInfo = PersonalInfo()
        personalInfo.contactEmail = email
        personalInfo.contactFirstName = firstName
        personalInfo.contactLastName = lastName

        return Completable.fromRunnable {
            val personalInfoList = database.personalInfoDao().queryPersonalInfoSync()

            if (!personalInfoList.isEmpty()) personalInfo.id = personalInfoList[0].id

            database.personalInfoDao().insert(personalInfo)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateShippingInfo(
            phoneNumber: String,
            address: String,
            city: String,
            state: String,
            country: String,
            postalCode: String): Completable {

        return Completable.fromRunnable {
            val insertAddress = Address()

            database.addressDao().queryByTypeSync(Address.Type.SHIPPING).getOrNull(0)?.let {
                insertAddress.id = it.id
            }

            insertAddress.phoneNumber = phoneNumber
            insertAddress.address = address
            insertAddress.city = city
            insertAddress.state = state
            insertAddress.country = country
            insertAddress.postalCode = postalCode
            database.addressDao().insert(insertAddress)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}
