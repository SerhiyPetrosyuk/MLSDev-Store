package com.mlsdev.mlsdevstore.data;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;

import java.util.Map;

import io.reactivex.Single;

public interface DataSource {

    /**
     * Default category tree id is needed to get the root {@link CategoryTree} which contains
     * a list of {@link CategoryTreeNode}s.
     */
    Single<String> getDefaultCategoryTreeId();

    /**
     * The root {@link CategoryTree} contains all main {@link CategoryTreeNode}s.
     */
    Single<CategoryTree> getRootCategoryTree();

    /**
     * Deletes all data in the local data storage
     */
    Single<CategoryTree> refreshRootCategoryTree();

    /**
     * @param queries - contains more than a category id.
     * @see <a href="https://developer.ebay.com/api-docs/buy/browse/resources/item_summary/methods/search#h2-input">search documentaion</a>
     */
    Single<SearchResult> searchItemsByCategoryId(Map<String, String> queries);

}
