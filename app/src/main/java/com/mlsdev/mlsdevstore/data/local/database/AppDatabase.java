package com.mlsdev.mlsdevstore.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter;
import com.mlsdev.mlsdevstore.data.local.database.dao.CategoriesDao;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

@Database(entities = {
        Category.class,
        CategoryTreeNode.class,
        CategoryTree.class
}, version = 1, exportSchema = false)
@TypeConverters({CategoryConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriesDao categoriesDao();
}
