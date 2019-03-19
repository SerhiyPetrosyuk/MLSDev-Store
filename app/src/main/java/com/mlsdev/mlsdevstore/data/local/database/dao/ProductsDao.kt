package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mlsdev.mlsdevstore.data.local.database.tables.ProductsTable
import com.mlsdev.mlsdevstore.data.model.product.Product
import io.reactivex.Single

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<Product>)

    @Query("select * from ${ProductsTable.NAME} limit 10")
    fun queryAllProducts(): Single<List<Product>>

    @Query("select * from ${ProductsTable.NAME}")
    fun queryAllProductsSync(): List<Product>

    @Query("select * from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 1")
    fun queryFavoriteProductsLiveData(): LiveData<List<Product>>

    @Query("select count(${ProductsTable.COLUMN_IS_FAVORITE}) from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 1")
    fun queryFavoritesCount(): Int

    @Delete
    fun delete(vararg product: Product)

    @Query("delete from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_IS_FAVORITE} = 0")
    fun deleteAllProducts()

    @Query("select count(${ProductsTable.COLUMN_ID}) from ${ProductsTable.NAME} where ${ProductsTable.COLUMN_ID} = :productId and ${ProductsTable.COLUMN_IS_FAVORITE} = 1")
    fun checkIfExists(productId: String): Int
}