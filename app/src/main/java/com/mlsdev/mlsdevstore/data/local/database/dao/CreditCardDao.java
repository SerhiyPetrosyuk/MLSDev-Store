package com.mlsdev.mlsdevstore.data.local.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.user.CreditCard;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CreditCardDao {

    @Insert
    void insert(CreditCard... creditCards);

    @Update
    void update(CreditCard... creditCards);

    @Delete
    void delete(CreditCard... creditCards);

    @Query("select * from " + Table.CREDIT_CARDS + " limit 1")
    Single<List<CreditCard>> queryCard();

    @Query("select * from " + Table.CREDIT_CARDS + " where id = :cardId")
    Single<List<CreditCard>> queryById(int cardId);

}
