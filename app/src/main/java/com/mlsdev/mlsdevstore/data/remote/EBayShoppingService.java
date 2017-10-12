package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.data.model.FindProductsRequest;
import com.mlsdev.mlsdevstore.data.model.FindProductsResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EBayShoppingService {

    @POST("shopping")
    Single<FindProductsResponse> findProducts(@Body FindProductsRequest findProductsRequest);

}
