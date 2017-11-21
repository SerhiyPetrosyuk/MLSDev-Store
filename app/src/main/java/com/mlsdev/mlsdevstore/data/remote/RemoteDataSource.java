package com.mlsdev.mlsdevstore.data.remote;

import android.util.Base64;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessTokenRequestBody;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService;
import com.mlsdev.mlsdevstore.data.remote.service.BuyService;
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key;

public class RemoteDataSource {
    private BuyService buyService;
    private AuthenticationService authenticationService;
    private SharedPreferencesManager sharedPreferencesManager;
    private TaxonomyService taxonomyService;

    @Inject
    public RemoteDataSource(BuyService buyService,
                            AuthenticationService authenticationService,
                            TaxonomyService taxonomyService,
                            SharedPreferencesManager sharedPreferencesManager) {
        this.buyService = buyService;
        this.authenticationService = authenticationService;
        this.taxonomyService = taxonomyService;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    public Single<AppAccessToken> getAppAccessToken() {
        String originalOAuthCredentials = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET;
        byte[] oAuthCredentialsBytes = new byte[0];
        try {
            oAuthCredentialsBytes = originalOAuthCredentials.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedOAuthCredentials = Base64.encodeToString(oAuthCredentialsBytes, Base64.NO_WRAP);
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic " + encodedOAuthCredentials);
        AppAccessTokenRequestBody body = new AppAccessTokenRequestBody();

        return prepareSingle(authenticationService.getAppAccessToken(headers, body.getFields()))
                .map(appAccessToken -> {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    long expirationDate = currentTime + appAccessToken.getExpiresIn();
                    appAccessToken.setExpirationDate(expirationDate);
                    return appAccessToken;
                })
                .doOnSuccess(appAccessToken -> {
                    sharedPreferencesManager.save(Key.APPLICATION_ACCESS_TOKEN, appAccessToken);
                });
    }

    public Single<String> getDefaultCategoryTreeId() {
        return prepareSingle(buyService.getDefaultCategoryTreeId())
                .map(CategoryTree::getCategoryTreeId);
    }

    public Single<CategoryTree> getRootCategoryTree() {
        return getDefaultCategoryTreeId()
                .flatMap(defaultCategoryTreeId -> buyService.getCategoryTree(defaultCategoryTreeId));
    }

    private <T> Single<T> prepareSingle(Single<T> single) {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
