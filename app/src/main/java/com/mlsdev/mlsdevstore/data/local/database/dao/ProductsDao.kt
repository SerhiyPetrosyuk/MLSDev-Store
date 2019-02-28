package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mlsdev.mlsdevstore.data.local.database.tables.ProductsTable
import com.mlsdev.mlsdevstore.data.model.item.Item
import io.reactivex.Single

@Dao
interface ProductsDao {

    @Insert
    fun insert(vararg product: Item)

    @Query("select * from ${ProductsTable.NAME}")
    fun queryAllProducts(): Single<List<Item>>

    @Delete
    fun delete(vararg product: Item)
}