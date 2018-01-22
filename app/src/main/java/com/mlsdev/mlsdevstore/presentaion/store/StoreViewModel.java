package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class StoreViewModel extends BaseViewModel {
    private DataSource dataSource;
    public final ObservableField<SearchResult> searchResult = new ObservableField<>();

    @Inject
    public StoreViewModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getProducts() {
        dataSource.searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver);
    }

    public void refresh() {
        dataSource.searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver);
    }

    private BaseObserver<SearchResult> searchResultBaseObserver = new BaseObserver<SearchResult>(this) {
        @Override
        public void onSuccess(SearchResult searchResult) {
            super.onSuccess(searchResult);
            StoreViewModel.this.searchResult.set(searchResult);
            StoreViewModel.this.searchResult.notifyChange();
        }
    };
}
