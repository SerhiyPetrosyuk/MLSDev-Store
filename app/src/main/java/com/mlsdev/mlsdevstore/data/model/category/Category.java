package com.mlsdev.mlsdevstore.data.model.category;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryId")
    private String categoryId;

    @SerializedName("categoryName")
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
