package com.mlsdev.mlsdevstore.data.remote;

import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.lang.ref.WeakReference;

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
        throwable.printStackTrace();
    }
}
