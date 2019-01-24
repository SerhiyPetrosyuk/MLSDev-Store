package com.mlsdev.mlsdevstore.presentaion.checkout

import androidx.databinding.ObservableField
import com.mlsdev.mlsdevstore.MLSDevStoreApplication
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class CheckoutViewModel @Inject
constructor(val application: MLSDevStoreApplication) : BaseViewModel() {

    val cardHolder = ObservableField<String>()
    val cardNumber = ObservableField<String>()
    val cardExpirationDate = ObservableField<String>()

}
