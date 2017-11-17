package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.databinding.ObservableBoolean;

import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.Calendar;

import javax.inject.Inject;

import static com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key;

public class SplashScreenViewModel extends BaseViewModel {
    public final ObservableBoolean appAccessTokenValid = new ObservableBoolean(false);
    private RemoteDataSource source;
    private SharedPreferencesManager preferencesManager;

    @Inject
    public SplashScreenViewModel(RemoteDataSource source, SharedPreferencesManager preferencesManager) {
        this.source = source;
        this.preferencesManager = preferencesManager;
    }

    public void checkAuthentication() {
        AppAccessToken token = preferencesManager.get(Key.APPLICATION_ACCESS_TOKEN, AppAccessToken.class);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (token == null || (token.getExpirationDate() - currentTime) <= 0) {
            source.getAppAccessToken()
                    .subscribe(new BaseObserver<AppAccessToken>(this){
                        @Override
                        public void onSuccess(AppAccessToken accessToken) {
                            super.onSuccess(accessToken);
                            appAccessTokenValid.set(accessToken != null);
                        }
                    });
        } else {
            appAccessTokenValid.set(true);
        }
    }
}
