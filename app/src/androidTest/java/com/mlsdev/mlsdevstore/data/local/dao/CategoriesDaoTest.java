package com.mlsdev.mlsdevstore.data.local.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

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
    public void testReadWriteCategory() {
        Category category = new Category();
        category.setCategoryId("some_cat_id");
        category.setCategoryName("some_cat_name");
        database.categoriesDao().insertCategories(category);

        database.categoriesDao().queryAllCategories()
                .test()
                .assertValue(categories -> categories != null && !categories.isEmpty() && categories.get(0).getCategoryId().equals(category.getCategoryId()));
    }

    @Test
    public void testReadWriteCategoryTreeNode() {
        Category category = new Category();
        Category category1 = new Category();
        category.setCategoryId("some_cat_id");
        category1.setCategoryId("some_cat_id1");
        CategoryTreeNode childCategoryTreeNode = new CategoryTreeNode();
        CategoryTreeNode childCategoryTreeNode1 = new CategoryTreeNode();

        childCategoryTreeNode.setParentCategoryTreeNodeHref("parent_href");
        childCategoryTreeNode.setCategory(category);
        childCategoryTreeNode.setLeafCategoryTreeNode(true);
        childCategoryTreeNode.setCategoryTreeNodeLevel(1);

        childCategoryTreeNode1.setParentCategoryTreeNodeHref("parent_href1");
        childCategoryTreeNode1.setCategory(category1);
        childCategoryTreeNode1.setLeafCategoryTreeNode(true);
        childCategoryTreeNode1.setCategoryTreeNodeLevel(1);

        database.categoriesDao().insertCategoryTreeNode(childCategoryTreeNode, childCategoryTreeNode1);
        database.categoriesDao().queryCategoryTreeNode()
                .test()
                .assertValue(Objects::nonNull)
                .assertValue(nodes -> nodes.size() == 2)
                .assertValue(nodes -> nodes.get(0).getCategory().getCategoryId().equals(category.getCategoryId()))
                .assertValue(nodes -> nodes.get(1).getCategory().getCategoryId().equals(category1.getCategoryId()));
    }

    @After
    public void after() throws Exception {
        database.close();
    }

}
