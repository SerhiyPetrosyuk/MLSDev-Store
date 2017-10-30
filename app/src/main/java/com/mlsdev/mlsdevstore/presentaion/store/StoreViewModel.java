package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;
import android.util.Log;

import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.data.model.category.GetCategoryInfoResponse;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class StoreViewModel extends BaseViewModel {
    private RemoteDataSource remoteDataSource;
    public final ObservableField<List<Category>> categories = new ObservableField<>();

    @Inject
    public StoreViewModel(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void getCategories() {
        remoteDataSource.getCategories().subscribe(
                (GetCategoryInfoResponse response) -> {
                    Log.d(LOG_TAG, "success");
                    categories.set(response.getCategoryArray().getCategoryList());
                },
                (Throwable throwable) -> {
                    Log.d(LOG_TAG, "error");
                });
    }
}
