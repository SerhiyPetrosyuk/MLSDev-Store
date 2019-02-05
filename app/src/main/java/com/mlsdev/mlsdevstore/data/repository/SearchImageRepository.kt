package com.mlsdev.mlsdevstore.data.repository

import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.image.CategoryImageEntity
import com.mlsdev.mlsdevstore.data.remote.service.SearchImageService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchImageRepository @Inject constructor(
        private val searchImageService: SearchImageService,
        private val database: AppDatabase
) {

    fun searchImage(categoryId: String, categoryName: String): Single<CategoryImageEntity> =
            database.categoryImagesDao().queryCategoryImageEntity(categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        return@flatMap if (it.isEmpty()) {
                            val name = Regex("[^A-Za-z ]").replace(categoryName, "").toLowerCase()
                            searchImageService.searchImage(name)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .flatMap { page ->
                                        val categoryImage = CategoryImageEntity(categoryId, page.getImageUrl())
                                        Completable.fromCallable { database.categoryImagesDao().insert(categoryImage) }
                                                .subscribeOn(Schedulers.io())
                                                .subscribe()
                                        Single.just(categoryImage)
                                    }
                        } else {
                            Single.just(it[0])
                        }
                    }

}