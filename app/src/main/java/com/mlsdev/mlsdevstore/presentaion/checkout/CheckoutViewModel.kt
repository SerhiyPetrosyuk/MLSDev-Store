package com.mlsdev.mlsdevstore.presentaion.checkout

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mlsdev.mlsdevstore.MLSDevStoreApplication
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.model.error.ValidationException
import com.mlsdev.mlsdevstore.data.model.user.CreditCard
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource
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
        val cart: Cart,
        val localDataSource: LocalDataSource,
        val remoteDataSource: RemoteDataSource) : BaseViewModel() {

    val cardHolder = ObservableField<String>()
    val cardNumber = ObservableField<String>()
    val cardExpirationDate = ObservableField<String>()
    val cvv = ObservableField<String>()
    val cardHolderError = ObservableField<String>()
    val cardNumberError = ObservableField<String>()
    val cardExpirationDateError = ObservableField<String>()
    val cvvError = ObservableField<String>()
    val cardTypeIcon = ObservableField<CreditCardTypeDetector.Type>()
    val cardTypeDetector = CreditCardTypeDetector()
    val totalSum = ObservableField<String>("00.00")
    val personalInfoErrorEvent = MutableLiveData<String>()

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

    fun cvvChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        cvvError.set(null)
    }

    fun onPlaceOrderClick() {
        checkNetworkConnection(appUtils) {
            val checkoutSessionValidator = GuestCheckoutSessionValidator(application)
            val paymentMethodValidator = PaymentMethodValidator(application)
            val credentials = PaymentMethod(
                    cardNumber.get(),
                    cardExpirationDate.get(),
                    cardHolder.get(),
                    cvv.get())

            compositeDisposable.add(paymentMethodValidator
                    .validate(credentials)
                    .flatMap { localDataSource.getGuestCheckoutSession() }
                    .flatMap { checkoutSessionValidator.validate(it) }
                    .map {
                        it.creditCard = CreditCard(
                                0,
                                getCardType(),
                                cardNumber.get(),
                                cvv.get(),
                                getExpirationMoth(),
                                getExpirationYear(),
                                it.shippingAddress)
                        return@map it
                    }
                    .subscribe(
                            {},
                            { handleError(it) }))
        }
    }

    private fun getCardType(): String = cardTypeIcon.get()?.value
            ?: CreditCardTypeDetector.Type.UNKNOWN.value

    private fun getExpirationMoth(): Int {

        cardExpirationDate.get()?.split("/")?.let {
            if (it.isNotEmpty())
                return it[0].toInt()
        }

        return 0
    }

    private fun getExpirationYear(): Int {

        cardExpirationDate.get()?.split("/")?.let {
            if (it.size > 1)
                return it[1].toInt()
        }

        return 0
    }

    override fun handleFieldsErrors(error: ValidationException) {
        cardNumberError.set(error.getErrorForField(FIELD_CARD_NUMBER))
        cardHolderError.set(error.getErrorForField(FIELD_CARD_HOLDER))
        cardExpirationDateError.set(error.getErrorForField(FIELD_EXPIRATION_DATE))
        cvvError.set(error.getErrorForField(FIELD_CVV))

        error.getErrorForField(FIELD_INIT_CHECKOUT_REQUEST)?.let { personalInfoErrorEvent.postValue(it) }
    }

}
