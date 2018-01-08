package com.mlsdev.mlsdevstore.data.local.database;

import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

public @interface Column {
    /**
     * @See {@link Category}
     * */
    String CATEGORY_ID = "category_id";
    String CATEGORY_NAME = "category_name";

    /**
     * @See {@link CategoryTreeNode}
     */
    String CATEGORY_TREE_NODE_ID = "category_tree_node_id";
    String CATEGORY_TREE_NODE_ID_FOREIGN_KEY = "category_tree_node_id_foreign_key";
    String CATEGORY = "category";
    String CATEGORY_TREE_NODE_LEVEL = "category_tree_node_level";
    String LEAF_CATEGORY_TREE_NODE = "leaf_category_tree_node";
    String PARENT_CATEGORY_TREE_NODE_HREF = "parent_category_tree_node_href";
}
