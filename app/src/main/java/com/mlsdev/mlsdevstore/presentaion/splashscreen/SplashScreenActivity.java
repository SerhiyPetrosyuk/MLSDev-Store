package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity;

public class SplashScreenActivity extends BaseActivity {
    private SplashScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(SplashScreenViewModel.class);

        getErrorInViewHandler().subscribeAllErrorCallbacks(viewModel, false);
        getErrorInViewHandler().setCloseAppAfterError(true);
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
