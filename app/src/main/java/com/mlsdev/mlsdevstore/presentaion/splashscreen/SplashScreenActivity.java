package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashScreenActivity extends DaggerAppCompatActivity {

    private SplashScreenViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel.class);
        initObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.checkAuthentication();
    }

    private void initObservers() {
        viewModel.appAccessTokenValid.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                boolean appAccessTokenValid = ((ObservableBoolean) observable).get();
                if (appAccessTokenValid) {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    SplashScreenActivity.this.finish();
                }
            }
        });

        viewModel.networkErrorOccurred.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (((ObservableBoolean) observable).get())
                    showError("Error", "Network error was occurred. Try again later.");
            }
        });

        viewModel.networkErrorOccurred.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (((ObservableBoolean) observable).get())
                    showError("Error", "Technical error was occurred. Try again later.");
            }
        });

    }

    private void showError(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("Close", (dialogInterface, i1) -> SplashScreenActivity.this.finish())
                .create();
        dialog.show();
    }

}
