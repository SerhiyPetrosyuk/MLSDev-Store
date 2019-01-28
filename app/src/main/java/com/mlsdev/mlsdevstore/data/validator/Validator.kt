package com.mlsdev.mlsdevstore.data.validator

import io.reactivex.Single

const val FIELD_EXPIRATION_DATE = "expiration_date"
const val FIELD_CARD_NUMBER = "card_number"
const val FIELD_CARD_HOLDER = "card_holder"

interface Validator<T> {
    fun validate(data: T): Single<T>
}