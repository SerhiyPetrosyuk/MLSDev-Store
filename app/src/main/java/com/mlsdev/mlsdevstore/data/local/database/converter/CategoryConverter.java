package com.mlsdev.mlsdevstore.data.local.database.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.mlsdev.mlsdevstore.data.model.category.Category;

public class CategoryConverter {

    @TypeConverter
    public static String toJson(Category category) {
        return category != null ? new Gson().toJson(category) : null;
    }

    @TypeConverter
    public static Category toModel(String json) {
        return (json != null && !json.isEmpty()) ? new Gson().fromJson(json, Category.class) : null;
    }

}
