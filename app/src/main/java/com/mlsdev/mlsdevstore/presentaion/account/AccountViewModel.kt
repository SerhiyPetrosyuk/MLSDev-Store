package com.mlsdev.mlsdevstore.presentaion.account

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject
constructor(
        context: Context,
        utils: Utils,
        private val localDataSource: LocalDataSource) : BaseViewModel() {

    init {
        this.context = context
        this.utils = utils
    }

    // Personal info
    val email = ObservableField<String>()
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()

    // Shipping info
    val phoneNumber = ObservableField<String>()
    val address = ObservableField<String>()
    val city = ObservableField<String>()
    val state = ObservableField<String>()
    val zip = ObservableField<String>()

    // Payment info
    val cardNumber = ObservableField<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun start() {
        checkNetworkConnection(utils!!) {
            compositeDisposable.add(localDataSource.guestCheckoutSession.subscribe(
                    { guestCheckoutSessionRequest ->
                        email.set(if (guestCheckoutSessionRequest.contactEmail != null) guestCheckoutSessionRequest.contactEmail else "")
                        firstName.set(if (guestCheckoutSessionRequest.contactFirstName != null) guestCheckoutSessionRequest.contactFirstName else "")
                        lastName.set(if (guestCheckoutSessionRequest.contactLastName != null) guestCheckoutSessionRequest.contactLastName else "")
                    },
                    { handleError(it) }))
        }
    }

    fun onEditShippingInfoClick() {
        // TODO: 3/6/18 start shipping info editing
    }

    fun onEditPaymentInfoClick() {
        // TODO: 3/6/18 start payment info editing
    }
}
