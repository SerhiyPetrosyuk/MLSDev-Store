package com.mlsdev.mlsdevstore.presentaion.checkout

import androidx.databinding.ObservableField
import com.mlsdev.mlsdevstore.MLSDevStoreApplication
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class CheckoutViewModel @Inject
constructor(
        val application: MLSDevStoreApplication,
        val appUtils: Utils) : BaseViewModel() {

    val cardHolder = ObservableField<String>()
    val cardNumber = ObservableField<String>()
    val cardExpirationDate = ObservableField<String>()
    val cardHolderError = ObservableField<String>()
    val cardNumberError = ObservableField<String>()
    val cardExpirationDateError = ObservableField<String>()

    fun cardNumberChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    fun cardHolderChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    fun carExpirationChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    fun onPlaceOrderClick() {
        checkNetworkConnection(appUtils) {

        }
    }

}
