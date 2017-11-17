package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements SingleObserver<T> {
    private final WeakReference<BaseViewModel> baseViewModelWeakReference;

    public BaseObserver(BaseViewModel viewModel) {
        this.baseViewModelWeakReference = new WeakReference<>(viewModel);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (!baseViewModelWeakReference.isEnqueued())
            baseViewModelWeakReference.get().compositeDisposable.add(disposable);
    }

    @Override
    public void onSuccess(T data) {

    }

    @Override
    public void onError(Throwable throwable) {

        if (throwable instanceof IOException)
            baseViewModelWeakReference.get().onNetworkErrorOccurred();
        else if (throwable instanceof SocketTimeoutException)
            baseViewModelWeakReference.get().onTechnicalErrorOccurred();

        throwable.printStackTrace();
    }
}
