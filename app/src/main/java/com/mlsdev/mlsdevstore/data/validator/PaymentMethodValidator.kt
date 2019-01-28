package com.mlsdev.mlsdevstore.data.validator

import com.mlsdev.mlsdevstore.data.model.error.ValidationException
import com.mlsdev.mlsdevstore.presentaion.utils.CreditCardTypeDetector
import io.reactivex.Single
import java.util.*

class PaymentMethodValidator : Validator<PaymentMethod> {

    override fun validate(data: PaymentMethod): Single<PaymentMethod> {
        val invalidFields = HashMap<String, String>()

        if (!validateExpirationDate(data.cardExpiration)) {
            invalidFields[FIELD_EXPIRATION_DATE] = "Incorrect"
        }

        if (data.cardHolderName.isNullOrBlank()) {
            invalidFields[FIELD_CARD_HOLDER] = "Can't be blank"
        }

        if (data.cardNumber.isNullOrBlank()) {
            invalidFields[FIELD_CARD_NUMBER] = "Can't be blank"
        } else if (!CreditCardTypeDetector().isValid(data.cardNumber)) {
            invalidFields[FIELD_CARD_NUMBER] = "Incorrect"
        }

        return if (invalidFields.isEmpty()) Single.just(data)
        else Single.error(ValidationException(invalidFields))
    }

    private fun validateExpirationDate(data: String?): Boolean {
        val yearsOffset = 10
        val maxMathValue = 12
        val maxYearValue = Calendar.getInstance().get(Calendar.YEAR) + yearsOffset
        val divider = '/'
        val monthAndYear: List<String>? = data?.split(divider)
        return (monthAndYear?.size == 2) &&
                (monthAndYear[0].toInt() <= maxMathValue) &&
                (monthAndYear[1].toInt() <= maxYearValue)
    }

}