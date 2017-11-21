package com.mlsdev.mlsdevstore.data.remote.service;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BuyService {

    @GET("commerce/taxonomy/v1_beta/get_default_category_tree_id?marketplace_id=EBAY_US")
    Single<CategoryTree> getDefaultCategoryTreeId();

    @GET("commerce/taxonomy/v1_beta/category_tree/{defaultCategoryTreeId}")
    Single<CategoryTree> getCategoryTree(@Path("defaultCategoryTreeId") String defaultCategoryTreeId);

    @GET("commerce/taxonomy/v1_beta/category_tree/{defaultCategoryTreeId}/get_category_subtree")
    Single<CategoryTree> getCategoryTree(@Path("defaultCategoryTreeId") String defaultCategoryTreeId,
                                         @Query("category_id") String categoryId);

}
