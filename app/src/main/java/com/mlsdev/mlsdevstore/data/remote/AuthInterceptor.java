package com.mlsdev.mlsdevstore.data.remote;

import android.support.annotation.NonNull;

import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public AuthInterceptor(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        AppAccessToken appAccessToken = sharedPreferencesManager.get(Key.APPLICATION_ACCESS_TOKEN, AppAccessToken.class);
        String appAccessTokenString = null;
        if (appAccessToken != null && appAccessToken.getAccessToken() != null)
            appAccessTokenString = appAccessToken.getAccessToken();

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + appAccessTokenString)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Encoding", "application/gzip")
                .build();

        return chain.proceed(request);
    }
}
