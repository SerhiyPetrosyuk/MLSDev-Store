package com.mlsdev.mlsdevstore.data.local.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.order.Address;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface AddressDao {

    @Insert
    void insert(Address... addresses);

    @Delete
    void delele(Address... addresses);

    @Update
    void update(Address... addresses);

    @Query("select * from " + Table.ADDRESSES + " where id = :addressId limit 1")
    Single<List<Address>> queryAddressById(int addressId);

}
