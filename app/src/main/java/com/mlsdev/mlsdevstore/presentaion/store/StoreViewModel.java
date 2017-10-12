package com.mlsdev.mlsdevstore.presentaion.store;

import android.util.Log;

import com.mlsdev.mlsdevstore.data.model.FindProductsRequest;
import com.mlsdev.mlsdevstore.data.model.FindProductsResponse;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class StoreViewModel extends BaseViewModel {
    private RemoteDataSource remoteDataSource;

    @Inject
    public StoreViewModel(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public void findProducts(){
        final FindProductsRequest findProductsRequest;
        findProductsRequest = new FindProductsRequest(
                "Samsung",
                10,
                true
        );

        remoteDataSource.findProducts(findProductsRequest)
                .subscribe(new Consumer<FindProductsResponse>() {
                    @Override
                    public void accept(FindProductsResponse findProductsResponse) throws Exception {
                        Log.d("LOGS", "success");
                    }
                });
    }
}
