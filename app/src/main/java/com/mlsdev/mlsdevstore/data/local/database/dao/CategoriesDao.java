package com.mlsdev.mlsdevstore.data.local.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
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

    @Query("delete from " + Table.CATEGORY_TREE_NODES)
    int deleteAllCategoryTreeNodel();

    @Query("select * from " + Table.CATEGORIES)
    Single<List<Category>> queryAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryTreeNode(CategoryTreeNode... categoryTreeNodes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryTreeNode(List<CategoryTreeNode> categoryTreeNodes);

    @Query("select * from " + Table.CATEGORY_TREE_NODES)
    Single<List<CategoryTreeNode>> queryCategoryTreeNode();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryTree(CategoryTree categoryTree);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryTree(CategoryTree... categoryTree);

    @Query("select * from " + Table.CATEGORY_TREES + " limit 1")
    Single<List<CategoryTree>> queryDefaultCategoryTree();

    @Query("delete from " + Table.CATEGORY_TREES)
    int deleteAllCategoryTrees();

}
