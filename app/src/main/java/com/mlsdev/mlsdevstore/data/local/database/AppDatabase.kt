package com.mlsdev.mlsdevstore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter
import com.mlsdev.mlsdevstore.data.local.database.dao.AddressDao
import com.mlsdev.mlsdevstore.data.local.database.dao.CategoriesDao
import com.mlsdev.mlsdevstore.data.local.database.dao.PersonalInfoDao
import com.mlsdev.mlsdevstore.data.model.category.Category
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo

@Database(entities = [
    Category::class,
    CategoryTreeNode::class,
    CategoryTree::class,
    Address::class,
    PersonalInfo::class
], version = 1, exportSchema = false)
@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun addressDao(): AddressDao
    abstract fun personalInfoDao(): PersonalInfoDao
}
