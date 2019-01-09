package com.mlsdev.mlsdevstore.dependency_injection.module;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "local_db").build();
    }

}
