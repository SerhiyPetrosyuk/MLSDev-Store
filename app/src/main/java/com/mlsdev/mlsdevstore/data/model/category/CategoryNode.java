package com.mlsdev.mlsdevstore.data.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryNode {

    @SerializedName("category")
    private Category category;

    @SerializedName("parentCategoryTreeNodeHref")
    private String parentCategoryTreeNodeHref;

    @SerializedName("childCategoryTreeNodes")
    private List<CategoryNode> childCategoryTreeNodes;

    @SerializedName("categoryTreeNodeLevel")
    private int categoryTreeNodeLevel;

    @SerializedName("leafCategoryTreeNode")
    private boolean leafCategoryTreeNode;

    public Category getCategory() {
        return category;
    }

    public String getParentCategoryTreeNodeHref() {
        return parentCategoryTreeNodeHref;
    }

    public List<CategoryNode> getChildCategoryTreeNodes() {
        return childCategoryTreeNodes;
    }

    public int getCategoryTreeNodeLevel() {
        return categoryTreeNodeLevel;
    }

    public boolean isLeafCategoryTreeNode() {
        return leafCategoryTreeNode;
    }
}
