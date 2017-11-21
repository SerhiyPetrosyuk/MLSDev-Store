package com.mlsdev.mlsdevstore.data.remote.service;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @see <a href="https://developer.ebay.com/api-docs/commerce/taxonomy/overview.html">eBay Taxonomy API documentation</a>
 * Use this api to retrieve categories and subcategories
 */
public interface TaxonomyService {

    @GET("commerce/taxonomy/v1_beta/get_default_category_tree_id?marketplace_id=EBAY_US")
    Single<String> getDefaultCategoryTreeId();

    @GET("commerce/taxonomy/v1_beta/category_tree/{category_tree_id}")
    Single<CategoryTree> getCategoryTree(@Path("category_tree_id") String categoryTreeId);

    @GET("commerce/taxonomy/v1_beta/category_tree/{category_tree_id}/get_category_subtree")
    Single<CategoryTree> getCategorySubtree(@Path("category_tree_id") String categoryTreeId,
                                            @Query("category_id") String categoryId);

}
