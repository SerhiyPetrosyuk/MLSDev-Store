package com.mlsdev.mlsdevstore.data.remote;

import android.util.Base64;

import com.mlsdev.mlsdevstore.BuildConfig;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessTokenRequestBody;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService;
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService;
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager.Key;

public class RemoteDataSource implements DataSource{
    private BrowseService browseService;
    private AuthenticationService authenticationService;
    private SharedPreferencesManager sharedPreferencesManager;
    private TaxonomyService taxonomyService;
    private AppDatabase database;

    @Inject
    public RemoteDataSource(BrowseService browseService,
                            AuthenticationService authenticationService,
                            TaxonomyService taxonomyService,
                            SharedPreferencesManager sharedPreferencesManager,
                            AppDatabase database) {
        this.browseService = browseService;
        this.authenticationService = authenticationService;
        this.taxonomyService = taxonomyService;
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.database = database;
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
                .doOnSuccess(appAccessToken -> sharedPreferencesManager.save(Key.APPLICATION_ACCESS_TOKEN, appAccessToken));
    }

    @Override
    public Single<String> getDefaultCategoryTreeId() {
        return prepareSingle(taxonomyService.getDefaultCategoryTreeId())
                .map(CategoryTree::getCategoryTreeId)
                .doOnSuccess(this::saveDefaultCategoryTreeId);
    }

    @Override
    public Single<CategoryTree> getRootCategoryTree() {
        return getDefaultCategoryTreeId()
                .flatMap(defaultCategoryTreeId -> prepareSingle(taxonomyService.getCategoryTree(defaultCategoryTreeId)))
                .doOnSuccess(this::saveCategoryTreeNodes);
    }

    @Override
    public Single<CategoryTree> refreshRootCategoryTree() {
        return getRootCategoryTree();
    }

    @Override
    public Single<SearchResult> searchItemsByCategoryId(Map<String, String> queries) {
        return prepareSingle(browseService.searchItemsByCategoryId(queries));
    }

    public  <T> Single<T> prepareSingle(Single<T> single) {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void saveCategoryTreeNodes(CategoryTree categoryTree) {
        Completable.create(e ->
                database.categoriesDao().insertCategoryTreeNode(categoryTree.getCategoryTreeNode().getChildCategoryTreeNodes()))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void saveDefaultCategoryTreeId(String categoryTreeId) {
        CategoryTree categoryTree = new CategoryTree();
        categoryTree.setCategoryTreeId(categoryTreeId);
        Completable.create(e -> database.categoriesDao().insertCategoryTree(categoryTree))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
