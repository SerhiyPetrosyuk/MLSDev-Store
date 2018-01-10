package com.mlsdev.mlsdevstore.data.local;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        Single<String> single = database.categoriesDao().queryDefaultCategoryTree()
                .flatMap(list -> !list.isEmpty()
                        ? Single.just(list.get(0).getCategoryTreeId())
                        : remoteDataSource.getDefaultCategoryTreeId());
        return remoteDataSource.prepareSingle(single);
    }

    @Override
    public Single<CategoryTree> getRootCategoryTree() {
        Single<List<CategoryTreeNode>> listSingle = database.categoriesDao().queryCategoryTreeNode();
        return remoteDataSource.prepareSingle(listSingle)
                .flatMap(nodes -> {
                    CategoryTree categoryTree = new CategoryTree();
                    CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
                    categoryTree.setCategoryTreeNode(categoryTreeNode);
                    categoryTreeNode.setChildCategoryTreeNodes(nodes);

                    return !nodes.isEmpty()
                            ? Single.just(categoryTree)
                            : remoteDataSource.getRootCategoryTree();
                });
    }

    @Override
    public Single<CategoryTree> refreshRootCategoryTree() {

        return Single.fromCallable(() -> {
            database.categoriesDao().deleteAllCategoryTreeNodel();
            database.categoriesDao().deleteAllCategoryTrees();
            return 1;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(integer -> remoteDataSource.refreshRootCategoryTree());
    }


}
