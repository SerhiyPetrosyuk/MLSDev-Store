package com.mlsdev.mlsdevstore.data.local;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class LocalDataSource implements DataSource {

    private RemoteDataSource remoteDataSource;
    private AppDatabase database;

    @Inject
    public LocalDataSource(RemoteDataSource remoteDataSource, AppDatabase database) {
        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }

    @Override
    public Single<String> getDefaultCategoryTreeId() {
        Single<String> singleRemote = remoteDataSource.getDefaultCategoryTreeId()
                .doOnSuccess(this::saveDefaultCategoryTreeId);


        Single<String> single = database.categoriesDao().queryDefaultCategoryTree()
                .flatMap(list -> !list.isEmpty()
                        ? Single.just(list.get(0).getCategoryTreeId())
                        : singleRemote);
        return remoteDataSource.prepareSingle(single);
    }

    @Override
    public Single<CategoryTree> getRootCategoryTree() {
        Single<CategoryTree> remoteSingle = remoteDataSource.getRootCategoryTree()
                .doOnSuccess(this::saveCategoryTreeNodes);


        Single<List<CategoryTreeNode>> listSingle = database.categoriesDao().queryCategoryTreeNode();
        return remoteDataSource.prepareSingle(listSingle)
                .flatMap(nodes -> {
                    CategoryTree categoryTree = new CategoryTree();
                    CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
                    categoryTree.setCategoryTreeNode(categoryTreeNode);
                    categoryTreeNode.setChildCategoryTreeNodes(nodes);

                    return !nodes.isEmpty()
                            ? Single.just(categoryTree)
                            : remoteSingle;
                });
    }

    private void saveCategoryTreeNodes(CategoryTree categoryTree) {
        Completable.create(e ->
                database.categoriesDao().insertCategoryTreeNode(categoryTree.getCategoryTreeNode().getChildCategoryTreeNodes()))
                .subscribe();
    }

    private void saveDefaultCategoryTreeId(String categoryTreeId) {
        CategoryTree categoryTree = new CategoryTree();
        categoryTree.setCategoryTreeId(categoryTreeId);
        Completable.create(e -> database.categoriesDao().insertCategoryTree(categoryTree))
                .subscribe();
    }
}
