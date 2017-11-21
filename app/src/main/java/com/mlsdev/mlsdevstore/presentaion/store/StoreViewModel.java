package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;
import android.util.Log;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTree;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class StoreViewModel extends BaseViewModel {
    private RemoteDataSource remoteDataSource;
    public final ObservableField<List<Object>> categories = new ObservableField<>();

    @Inject
    public StoreViewModel(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void getCategories() {

        remoteDataSource.getRootCategoryTree()
                .subscribe(new BaseObserver<CategoryTree>(this){
                    @Override
                    public void onSuccess(CategoryTree defaultCategoryTree) {
                        super.onSuccess(defaultCategoryTree);
                        Log.d(LOG_TAG, "Default category tree id: " + defaultCategoryTree.getCategoryTreeId());
                    }
                });

    }
}
