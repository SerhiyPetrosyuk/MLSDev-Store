package com.mlsdev.mlsdevstore.data.remote.service;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface BrowseService {

    @GET("/buy/browse/v1/item_summary/search")
    Single<SearchResult> searchItemsByCategoryId(@QueryMap Map<String, String> queries);

    @GET("/buy/browse/v1/item/{id}")
    Single<Item> getItem(@Path("id") String itemId);

}
