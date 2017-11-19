package com.mlsdev.mlsdevstore.presentaion;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected ErrorInViewHandler errorInViewHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorInViewHandler.setContext(this);
    }
}
