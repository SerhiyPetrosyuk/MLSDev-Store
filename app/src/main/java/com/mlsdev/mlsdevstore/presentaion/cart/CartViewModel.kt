package com.mlsdev.mlsdevstore.presentaion.cart

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.databinding.ObservableBoolean
import android.view.View

import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel

import javax.inject.Inject

class CartViewModel @Inject
constructor(private val cart: Cart) : BaseViewModel(), Cart.OnItemCountChangeListener {
    val cartIsEmpty = ObservableBoolean(true)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        cart.addOnItemCountChangeListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        cart.removeOnItemCountChangeListener(this)
    }

    override fun onItemCountChanged(count: Int) {
        cartIsEmpty.set(count == 0)
    }

    fun onCheckoutClick(button: View) {
        CheckoutActivity.launch(button.context)
    }
}
