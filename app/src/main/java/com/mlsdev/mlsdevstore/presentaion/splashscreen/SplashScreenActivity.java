package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashScreenActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashScreenViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel.class);
        viewModel.checkAuthentication();
    }

}
