package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.util.Log;

import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class SplashScreenViewModel extends BaseViewModel {
    private RemoteDataSource source;

    @Inject
    public SplashScreenViewModel(RemoteDataSource source) {
        this.source = source;
    }

    public void checkAuthentication() {
        compositeDisposable.add(
                source.getAppAccessToken().subscribe(
                        (AppAccessToken accessToken) -> {
                            Log.d(LOG_TAG, accessToken != null ? accessToken.toString() + "" : "null");
                            Log.d(LOG_TAG, accessToken != null ? accessToken.getRefreshToken() + "" : "null");
                            Log.d(LOG_TAG, accessToken != null ? accessToken.getAccessToken() + "" : "null");
                        },
                        Throwable::printStackTrace)
        );
    }
}
