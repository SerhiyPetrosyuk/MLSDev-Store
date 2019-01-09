package com.mlsdev.mlsdevstore.data.local.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.user.Address;

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

    @Query("select * from " + Table.ADDRESSES + " where type is :addressType limit 1")
    Single<List<Address>> queryByType(@Address.Type String addressType);

}
