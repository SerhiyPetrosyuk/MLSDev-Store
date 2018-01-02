package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class StoreViewModel extends BaseViewModel {
    private RemoteDataSource remoteDataSource;
    public final ObservableField<List<CategoryTreeNode>> categories = new ObservableField<>();

    @Inject
    public StoreViewModel(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void getCategories() {
        // TODO: 1/2/18 get five or more random categories and show about 10 products from each one

        remoteDataSource.getRootCategoryTree()
                .subscribe(new BaseObserver<CategoryTree>(this) {
                    @Override
                    public void onSuccess(CategoryTree defaultCategoryTree) {
                        super.onSuccess(defaultCategoryTree);
                        categories.set(defaultCategoryTree.getCategoryTreeNode().getChildCategoryTreeNodes());
                    }
                });


    }
}
