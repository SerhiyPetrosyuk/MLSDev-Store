package com.mlsdev.mlsdevstore.data.validator

import io.reactivex.Single

class PaymentMethodValidator : Validator<PaymentMethod> {

    override fun validate(data: PaymentMethod): Single<PaymentMethod> {
        val invalidFields = HashMap<String, String>()

        return Single.just(data)
    }

}