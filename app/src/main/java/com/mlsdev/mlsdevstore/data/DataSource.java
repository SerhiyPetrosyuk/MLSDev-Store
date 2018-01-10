package com.mlsdev.mlsdevstore.data;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

import io.reactivex.Single;

public interface DataSource {

    /**
     * Default category tree id is needed to get the root {@link CategoryTree} which contains
     * a list of {@link CategoryTreeNode}s.
     * */
    Single<String> getDefaultCategoryTreeId();

    /**
     * The root {@link CategoryTree} contains all main {@link CategoryTreeNode}s.
     * */
    Single<CategoryTree> getRootCategoryTree();

    /**
     * Deletes all data in the local data storage
     * */
    Single<CategoryTree> refreshRootCategoryTree();

}
