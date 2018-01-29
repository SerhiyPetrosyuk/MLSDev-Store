package com.mlsdev.mlsdevstore.presentaion.utils;

import android.databinding.ObservableBoolean;

public class CustomObservableBoolean extends ObservableBoolean {

    private boolean value;

    public CustomObservableBoolean(boolean value) {
        this.value = value;
    }

    public CustomObservableBoolean() {

    }

    @Override
    public boolean get() {
        return this.value;
    }

    @Override
    public void set(boolean value) {
        this.value = value;
        notifyChange();
    }
}
