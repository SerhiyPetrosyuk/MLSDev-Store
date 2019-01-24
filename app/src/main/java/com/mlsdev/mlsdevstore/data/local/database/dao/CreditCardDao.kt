package com.mlsdev.mlsdevstore.data.local.database.dao

import androidx.room.*

import com.mlsdev.mlsdevstore.data.local.database.Table
import com.mlsdev.mlsdevstore.data.model.user.CreditCard

import io.reactivex.Single

@Dao
interface CreditCardDao {

    @Insert
    fun insert(vararg creditCards: CreditCard)

    @Update
    fun update(vararg creditCards: CreditCard)

    @Delete
    fun delete(vararg creditCards: CreditCard)

    @Query("select * from ${Table.CREDIT_CARDS} limit 1")
    fun queryCard(): Single<List<CreditCard>>

    @Query("select * from ${Table.CREDIT_CARDS} where id = :cardId")
    fun queryById(cardId: Int): Single<List<CreditCard>>

}
