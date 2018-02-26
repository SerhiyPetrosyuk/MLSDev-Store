package com.mlsdev.mlsdevstore.presentaion.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.widget.Toast;

import com.mlsdev.mlsdevstore.data.cart.Cart;
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
        // TODO: 2/26/18 start the checkout screen
        Toast.makeText(button.getContext(), "it will start the checkout screen", Toast.LENGTH_SHORT).show();
    }
}
