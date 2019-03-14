package com.mlsdev.mlsdevstore.data.repository

import androidx.paging.PagedList
import io.reactivex.Observable

interface Repository<T> {
    fun getData() : Observable<PagedList<T>>
}