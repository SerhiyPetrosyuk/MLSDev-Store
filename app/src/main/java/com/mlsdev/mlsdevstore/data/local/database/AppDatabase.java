package com.mlsdev.mlsdevstore.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter;
import com.mlsdev.mlsdevstore.data.local.database.dao.CategoriesDao;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.model.user.Address;
import com.mlsdev.mlsdevstore.data.model.user.CreditCard;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;

@Database(entities = {
        Category.class,
        CategoryTreeNode.class,
        CategoryTree.class,
        Address.class,
        CreditCard.class,
        PersonalInfo.class
}, version = 1, exportSchema = false)
@TypeConverters({CategoryConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriesDao categoriesDao();
}
