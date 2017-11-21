package com.mlsdev.mlsdevstore.data.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryTree {

    @SerializedName("categoryTreeId")
    private String categoryTreeId;

    @SerializedName("categoryTreeVersion")
    private String categoryTreeVersion;

    @SerializedName(value = "rootCategoryNode", alternate = {"categorySubtreeNode"})
    private CategoryTreeNode categoryTreeNode;

    @SerializedName("applicableMarketplaceIds")
    private List<String> applicableMarketplaceIds;

    public String getCategoryTreeId() {
        return categoryTreeId;
    }

    public String getCategoryTreeVersion() {
        return categoryTreeVersion;
    }

    public CategoryTreeNode getCategoryTreeNode() {
        return categoryTreeNode;
    }

    public List<String> getApplicableMarketplaceIds() {
        return applicableMarketplaceIds;
    }
}
