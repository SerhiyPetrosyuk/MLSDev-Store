package com.mlsdev.mlsdevstore.data.remote;

import android.util.Base64;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessTokenRequestBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {
    private BuyService buyService;
    private AuthenticationService authenticationService;

    @Inject
    public RemoteDataSource(BuyService buyService, AuthenticationService authenticationService) {
        this.buyService = buyService;
        this.authenticationService = authenticationService;
    }

    public Single<AppAccessToken> getAppAccessToken() {
        String originalOAuthCredentials = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET;
        byte[] oAuthCredentialsBytes = new byte[0];
        try {
            oAuthCredentialsBytes = originalOAuthCredentials.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedOAuthCredentials = Base64.encodeToString(oAuthCredentialsBytes, Base64.DEFAULT);
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", encodedOAuthCredentials);
        AppAccessTokenRequestBody body = new AppAccessTokenRequestBody();

        return prepareSingle(authenticationService.getAppAccessToken(headers, body));
    }

    private <T> Single<T> prepareSingle(Single<T> single) {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
