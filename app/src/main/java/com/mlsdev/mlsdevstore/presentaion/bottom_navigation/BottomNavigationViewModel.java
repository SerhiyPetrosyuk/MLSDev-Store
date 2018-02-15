package com.mlsdev.mlsdevstore.presentaion.bottom_navigation;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableInt;

import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class BottomNavigationViewModel extends BaseViewModel implements Cart.OnItemCountChangeListener {
    public final ObservableInt itemCount = new ObservableInt(0);
    private Cart cart;

    @Inject
    public BottomNavigationViewModel(Cart cart) {
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
        itemCount.set(count);
    }
}
