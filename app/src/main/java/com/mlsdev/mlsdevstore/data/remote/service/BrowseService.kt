package com.mlsdev.mlsdevstore.data.remote.service

import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.SearchResult

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface BrowseService {

    @GET("/buy/browse/v1/item_summary/search")
    fun searchItemsByCategoryId(@QueryMap queries: Map<String, String>): Single<SearchResult>

    @GET("/buy/browse/v1/item/{id}?fieldgroups=PRODUCT")
    fun getItem(@Path("id") itemId: String): Single<Item>

}
