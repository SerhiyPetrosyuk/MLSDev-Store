package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mlsdev.mlsdevstore.data.local.database.tables.ProductsTable
import com.mlsdev.mlsdevstore.data.model.product.Product
import io.reactivex.Single

@Dao
interface ProductsDao {

    @Insert
    fun insert(vararg product: Product)

    @Insert
    fun insert(products: List<Product>)

    @Query("select * from ${ProductsTable.NAME}")
    fun queryAllProducts(): Single<List<Product>>

    @Query("select * from ${ProductsTable.NAME}")
    fun queryAllProductsSync(): List<Product>

    @Query("select * from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 1")
    fun queryFavoriteProductsSync(): List<Product>

    @Query("select count(${ProductsTable.COLUMN_IS_FAVORITE}) from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 1")
    fun queryFavoritesCount(): Int

    @Delete
    fun delete(vararg product: Product)

    @Query("delete from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 0")
    fun deleteAllProducts()
}