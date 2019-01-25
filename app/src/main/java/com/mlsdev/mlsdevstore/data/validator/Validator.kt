package com.mlsdev.mlsdevstore.data.validator

import io.reactivex.Single

interface Validator<T> {
    fun validate(data: T): Single<T>
}