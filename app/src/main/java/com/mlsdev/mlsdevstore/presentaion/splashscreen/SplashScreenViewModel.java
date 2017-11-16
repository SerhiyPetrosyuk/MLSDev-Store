package com.mlsdev.mlsdevstore.presentaion.splashscreen;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.Calendar;
import java.util.Observable;

import javax.inject.Inject;

import static com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.*;

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
            compositeDisposable.add(source.getAppAccessToken().subscribe(
                    (AppAccessToken accessToken) -> appAccessTokenValid.set(accessToken != null),
                    Throwable::printStackTrace));
        } else {
            appAccessTokenValid.set(true);
        }
    }
}
