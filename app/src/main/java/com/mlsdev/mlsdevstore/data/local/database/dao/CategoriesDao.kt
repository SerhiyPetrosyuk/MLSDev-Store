package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.room.*
import com.mlsdev.mlsdevstore.data.local.database.Table
import com.mlsdev.mlsdevstore.data.model.category.Category
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import io.reactivex.Single

@Dao
interface CategoriesDao {

    @Insert
    fun insertCategories(vararg category: Category)

    @Update
    fun updateCategories(vararg categories: Category)

    @Delete
    fun deleteCategories(vararg categories: Category)

    @Query("delete from ${Table.CATEGORY_TREE_NODES}")
    fun deleteAllCategoryTreeNodes(): Int

    @Query("select * from ${Table.CATEGORIES}")
    fun queryAllCategories(): Single<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryTreeNode(vararg categoryTreeNodes: CategoryTreeNode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryTreeNode(categoryTreeNodes: List<CategoryTreeNode>)

    @Query("select * from ${Table.CATEGORY_TREE_NODES}")
    fun queryCategoryTreeNode(): Single<List<CategoryTreeNode>>

    @Query("select * from ${Table.CATEGORY_TREE_NODES}")
    fun queryCategoryTreeNodeSync(): List<CategoryTreeNode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryTree(categoryTree: CategoryTree)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryTree(vararg categoryTree: CategoryTree)

    @Query("select * from ${Table.CATEGORY_TREES} limit 1")
    fun queryDefaultCategoryTree(): Single<List<CategoryTree>>

    @Query("delete from ${Table.CATEGORY_TREES}")
    fun deleteAllCategoryTrees(): Int

}
