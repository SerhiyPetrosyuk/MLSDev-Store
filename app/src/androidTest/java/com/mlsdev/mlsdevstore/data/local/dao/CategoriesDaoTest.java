package com.mlsdev.mlsdevstore.data.local.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.category.Category;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.functions.Predicate;

@RunWith(AndroidJUnit4.class)
public class CategoriesDaoTest {
    private AppDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void before() throws Exception {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void testReadWriteCategory(){
        Category category = new Category();
        category.setCategoryId("some_cat_id");
        category.setCategoryName("some_cat_name");
        database.categoriesDao().insertCategories(category);

        database.categoriesDao().queryAllCategories()
                .test()
                .assertValue(categories -> categories != null && !categories.isEmpty() && categories.get(0).getCategoryId().equals(category.getCategoryId()));
    }

    @After
    public void after() throws Exception {
        database.close();
    }

}
