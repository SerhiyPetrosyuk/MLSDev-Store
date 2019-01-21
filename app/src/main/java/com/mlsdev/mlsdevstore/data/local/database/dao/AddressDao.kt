package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mlsdev.mlsdevstore.data.local.database.Table
import com.mlsdev.mlsdevstore.data.model.user.Address

import io.reactivex.Single

@Dao
interface AddressDao {

    @Insert
    fun insert(vararg addresses: Address)

    @Delete
    fun delete(vararg addresses: Address)

    @Update
    fun update(vararg addresses: Address)

    @Query("select * from ${Table.ADDRESSES} where type is :addressType limit 1")
    fun queryByType(@Address.Type addressType: String): Single<List<Address>>

}
