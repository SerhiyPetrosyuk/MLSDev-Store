package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.data.model.category.GetCategoryInfoRequest;
import com.mlsdev.mlsdevstore.data.model.category.GetCategoryInfoResponse;
import com.mlsdev.mlsdevstore.data.model.product.FindProductsRequest;
import com.mlsdev.mlsdevstore.data.model.product.FindProductsResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EBayShoppingService {

    @POST("shopping")
    @Headers("X-EBAY-API-CALL-NAME: FindProducts")
    Single<FindProductsResponse> findProducts(@Body FindProductsRequest findProductsRequest);

    @POST("shopping")
    @Headers({"X-EBAY-API-CALL-NAME: GetCategoryInfo"})
    Single<GetCategoryInfoResponse> getCategoryInfo(@Body GetCategoryInfoRequest getCategoryInfoRequest);

}
