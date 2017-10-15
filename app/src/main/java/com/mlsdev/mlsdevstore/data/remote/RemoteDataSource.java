package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.data.model.category.GetCategoryInfoRequest;
import com.mlsdev.mlsdevstore.data.model.category.GetCategoryInfoResponse;
import com.mlsdev.mlsdevstore.data.model.product.FindProductsRequest;
import com.mlsdev.mlsdevstore.data.model.product.FindProductsResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {
    private EBayShoppingService shoppingService;

    @Inject
    public RemoteDataSource(EBayShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    public Single<FindProductsResponse> findProducts(FindProductsRequest request) {
        return prepareSingle(shoppingService.findProducts(request));
    }

    public Single<GetCategoryInfoResponse> getCategories() {
        return prepareSingle(shoppingService.getCategoryInfo(new GetCategoryInfoRequest()));

    }

    private <T> Single<T> prepareSingle(Single<T> single) {
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
