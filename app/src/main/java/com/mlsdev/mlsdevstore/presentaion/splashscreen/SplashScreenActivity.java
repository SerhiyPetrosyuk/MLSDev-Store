package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;

public class SplashScreenActivity extends BaseActivity {
    private SplashScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel.class);

        errorInViewHandler.subscribeAllErrorCallbacks(viewModel, false);
        errorInViewHandler.setCloseAppAfterError(true);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.checkAuthentication();
    }

}
