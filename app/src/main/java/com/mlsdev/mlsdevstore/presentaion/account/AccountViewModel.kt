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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun start() {
        checkNetworkConnection(utils!!) {
            compositeDisposable.add(localDataSource.getGuestCheckoutSession().subscribe(
                    { guestCheckoutSessionRequest ->
                        email.set(guestCheckoutSessionRequest.contactEmail)
                        firstName.set(guestCheckoutSessionRequest.contactFirstName)
                        lastName.set(guestCheckoutSessionRequest.contactLastName)
                        phoneNumber.set(guestCheckoutSessionRequest.shippingAddress.phoneNumber)
                        address.set(guestCheckoutSessionRequest.shippingAddress.address)
                        city.set(guestCheckoutSessionRequest.shippingAddress.city)
                        state.set(guestCheckoutSessionRequest.shippingAddress.state)
                        zip.set(guestCheckoutSessionRequest.shippingAddress.postalCode)
                    },
                    { handleError(it) }))
        }
    }
}
