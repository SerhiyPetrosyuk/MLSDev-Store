package com.mlsdev.mlsdevstore.data.remote;

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
        return shoppingService.findProducts(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
