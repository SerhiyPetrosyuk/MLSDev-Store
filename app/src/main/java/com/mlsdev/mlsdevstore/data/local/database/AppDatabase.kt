package com.mlsdev.mlsdevstore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter
import com.mlsdev.mlsdevstore.data.local.database.dao.*
import com.mlsdev.mlsdevstore.data.model.category.Category
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.data.model.image.CategoryImageEntity
import com.mlsdev.mlsdevstore.data.model.item.Image
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.data.model.item.Price
import com.mlsdev.mlsdevstore.data.model.order.GuestCheckoutSession
import com.mlsdev.mlsdevstore.data.model.user.Address
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo

@Database(entities = [
    Category::class,
    CategoryTreeNode::class,
    CategoryTree::class,
    Address::class,
    PersonalInfo::class,
    GuestCheckoutSession::class,
    CategoryImageEntity::class,
    Item::class,
    Price::class,
    Image::class
], version = 1, exportSchema = false)
@TypeConverters(CategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun addressDao(): AddressDao
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun checkoutSessionDao(): CheckoutSessionDao
    abstract fun categoryImagesDao(): CategoryImagesDao
    abstract fun productsDao(): ProductsDao
    abstract fun imagesDao(): ImagesDao
    abstract fun pricesDao(): PricesDao
}
