package com.mlsdev.mlsdevstore.presentaion.checkout

import androidx.databinding.ObservableField
import com.mlsdev.mlsdevstore.MLSDevStoreApplication
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.model.error.ValidationException
import com.mlsdev.mlsdevstore.data.validator.*
import com.mlsdev.mlsdevstore.presentaion.utils.CreditCardTypeDetector
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import java.text.DecimalFormat
import javax.inject.Inject

class CheckoutViewModel @Inject
constructor(
        val application: MLSDevStoreApplication,
        val appUtils: Utils,
        val cart: Cart) : BaseViewModel() {

    val cardHolder = ObservableField<String>()
    val cardNumber = ObservableField<String>()
    val cardExpirationDate = ObservableField<String>()
    val cardHolderError = ObservableField<String>()
    val cardNumberError = ObservableField<String>()
    val cardExpirationDateError = ObservableField<String>()
    val cardTypeIcon = ObservableField<CreditCardTypeDetector.Type>()
    val cardTypeDetector = CreditCardTypeDetector()
    val totalSum = ObservableField<String>("00.00")

    init {
        totalSum.set(DecimalFormat("#0.00").format(cart.getTotalSum()))
    }

    fun cardNumberChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        cardNumberError.set(null)
        cardTypeIcon.set(cardTypeDetector.getCardType(s.toString()))
    }

    fun cardHolderChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        cardHolderError.set(null)
    }

    fun cardExpirationChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        cardExpirationDateError.set(null)
    }

    fun onPlaceOrderClick() {
        checkNetworkConnection(appUtils) {
            val validator = PaymentMethodValidator()
            val credentials = PaymentMethod(
                    cardNumber.get(),
                    cardExpirationDate.get(),
                    cardHolder.get())

            compositeDisposable.add(validator
                    .validate(credentials)
                    .subscribe(
                            {},
                            { handleError(it) }))
        }
    }

    override fun handleFieldsErrors(error: ValidationException) {
        cardNumberError.set(error.getErrorForField(FIELD_CARD_NUMBER))
        cardHolderError.set(error.getErrorForField(FIELD_CARD_HOLDER))
        cardExpirationDateError.set(error.getErrorForField(FIELD_EXPIRATION_DATE))
    }

}
