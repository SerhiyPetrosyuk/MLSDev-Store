package com.mlsdev.mlsdevstore.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mlsdev.mlsdevstore.data.local.database.dao.CategoriesDao;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

@Database(entities = {Category.class, CategoryTreeNode.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriesDao categoriesDao();
}
