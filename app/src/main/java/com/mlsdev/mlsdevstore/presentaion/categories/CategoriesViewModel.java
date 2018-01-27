package com.mlsdev.mlsdevstore.presentaion.categories;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class CategoriesViewModel extends BaseViewModel {
    private DataSource dataSource;
    public final ObservableField<List<CategoryTreeNode>> listObservableField = new ObservableField<>();

    @Inject
    public CategoriesViewModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getRootCategories() {
        dataSource.getRootCategoryTree()
                .subscribe(new BaseObserver<CategoryTree>(this) {
                    @Override
                    public void onSuccess(CategoryTree data) {
                        super.onSuccess(data);
                        listObservableField.set(data.getCategoryTreeNode().getChildCategoryTreeNodes());
                    }
                });
    }
}
