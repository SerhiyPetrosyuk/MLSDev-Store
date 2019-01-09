package com.mlsdev.mlsdevstore.presentaion.store;

import androidx.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class StoreViewModel extends BaseViewModel {
    public final ObservableField<SearchResult> searchResult = new ObservableField<>();

    @Inject
    public StoreViewModel(DataSource dataSource, Utils utils) {
        this.setDataSource(dataSource);
        this.setUtils(utils);
    }

    public void getProducts() {
        if (!getUtils().isNetworkAvailable()) {
            onNetworkErrorOccurred();
            return;
        }

        isLoading().set(true);
        isLoading().notifyChange();
        getDataSource().searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver);
    }

    public void refresh() {
        if (!getUtils().isNetworkAvailable()) {
            onNetworkErrorOccurred();
            return;
        }

        isRefreshing().set(true);
        getDataSource().resetSearchResults();
        getDataSource().searchItemsByRandomCategory()
                .subscribe(searchResultBaseObserver);
    }

    public void loadMoreItemsFromRandomCategory() {
        if (!getUtils().isNetworkAvailable()) {
            onNetworkErrorOccurred();
            return;
        }

        getDataSource().searchMoreItemsByRandomCategory()
                .subscribe(new BaseObserver<SearchResult>(this) {
                    @Override
                    public void onSuccess(SearchResult data) {
                        super.onSuccess(data);
                        searchResult.get().setOffset(data.getOffset());
                        searchResult.get().getItemSummaries().addAll(data.getItemSummaries());
                        searchResult.notifyChange();
                    }
                });
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
