package com.mlsdev.mlsdevstore.presentaion.account

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSessionRequest
import com.mlsdev.mlsdevstore.data.remote.BaseObserver
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject
constructor(context: Context, val localDataSource: LocalDataSource) : BaseViewModel() {

    init {
        this.context = context
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
        localDataSource.guestCheckoutSession
                .subscribe(object : BaseObserver<GuestCheckoutSessionRequest>(this) {
                    override fun onSuccess(data: GuestCheckoutSessionRequest) {
                        super.onSuccess(data)
                        email.set(if (data.contactEmail != null) data.contactEmail else "")
                        firstName.set(if (data.contactFirstName != null) data.contactFirstName else "")
                        lastName.set(if (data.contactLastName != null) data.contactLastName else "")
                    }
                })
    }

    fun onEditPersonalInfoClick() {
        EditPersonalInfoActivity.launch(context!!)
    }

    fun onEditShippingInfoClick() {
        // TODO: 3/6/18 start shipping info editing
    }

    fun onEditPaymentInfoClick() {
        // TODO: 3/6/18 start payment info editing
    }
}
