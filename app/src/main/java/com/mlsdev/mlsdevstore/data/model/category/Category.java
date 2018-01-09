package com.mlsdev.mlsdevstore.data.model.category;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mlsdev.mlsdevstore.data.local.database.Table;

@Entity(tableName = Table.CATEGORIES)
public class Category {

    @NonNull
    @PrimaryKey
    private String categoryId = "default";

    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
