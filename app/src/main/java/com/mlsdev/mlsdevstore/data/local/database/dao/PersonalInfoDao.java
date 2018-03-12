package com.mlsdev.mlsdevstore.data.local.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.model.user.PersonalInfo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PersonalInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PersonalInfo personalInfo);

    @Update
    void update(PersonalInfo personalInfo);

    @Delete
    void delete(PersonalInfo personalInfo);

    @Query("select * from " + Table.PERSONAL_INFO + " limit 1")
    Single<List<PersonalInfo>> queryPersonalInfo();

    @Query("select * from " + Table.PERSONAL_INFO + " limit 1")
    List<PersonalInfo> queryPersonalInfoSync();

}
