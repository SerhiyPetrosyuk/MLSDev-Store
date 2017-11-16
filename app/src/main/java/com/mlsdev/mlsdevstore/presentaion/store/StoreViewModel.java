package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

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
    }
}
