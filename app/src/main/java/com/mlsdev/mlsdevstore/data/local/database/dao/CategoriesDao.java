package com.mlsdev.mlsdevstore.data.local.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CategoriesDao {

    @Insert
    void insertCategories(Category... category);

    @Update
    void updateCategories(Category... categories);

    @Delete
    void deleteCategories(Category... categories);

    @Query("select * from " + Table.CATEGORIES)
    Single<List<Category>> queryAllCategories();

    @Insert
    void insertCategoryTreeNode(CategoryTreeNode... categoryTreeNodes);

    @Query("select * from " + Table.CATEGORY_TREE_NODES)
    Single<List<CategoryTreeNode>> queryCategoryTreeNode();

}
