package com.mlsdev.mlsdevstore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter
import com.mlsdev.mlsdevstore.data.local.database.dao.*
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.image.CategoryImageEntity
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSession
import com.mlsdev.mlsdevstore.data.model.product.Product
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo

@Database(entities = [
    CategoryTreeNode::class,
    CategoryTree::class,
    Address::class,
    PersonalInfo::class,
    GuestCheckoutSession::class,
    CategoryImageEntity::class,
    Product::class
], version = 1, exportSchema = false)
@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun addressDao(): AddressDao
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun checkoutSessionDao(): CheckoutSessionDao
    abstract fun categoryImagesDao(): CategoryImagesDao
    abstract fun productsDao(): ProductsDao
}
