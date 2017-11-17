package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel{
    protected static final String LOG_TAG = "ViewModel.Log.Tag";
    public final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void onTechnicalErrorOccurred() {

    }

    public void onNetworkErrorOccurred() {

    }

    public void onAuthorizationErrorOccurred() {

    }
}
