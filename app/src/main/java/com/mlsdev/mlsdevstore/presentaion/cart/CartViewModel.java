package com.mlsdev.mlsdevstore.presentaion.cart;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.databinding.ObservableBoolean;
import android.view.View;

import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.presentaion.checkout.CheckoutActivity;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class CartViewModel extends BaseViewModel implements Cart.OnItemCountChangeListener {
    public final ObservableBoolean cartIsEmpty = new ObservableBoolean(true);
    private Cart cart;

    @Inject
    public CartViewModel(Cart cart) {
        this.cart = cart;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        cart.addOnItemCountChangeListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        cart.removeOnItemCountChangeListener(this);
    }

    @Override
    public void onItemCountChanged(int count) {
        cartIsEmpty.set(count == 0);
    }

    public void onCheckoutClick(View button) {
        CheckoutActivity.launch(button.getContext());
    }
}
