package com.mlsdev.mlsdevstore.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mlsdev.mlsdevstore.data.local.database.dao.CategoriesDao;
import com.mlsdev.mlsdevstore.data.model.category.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriesDao categoriesDao();
}
