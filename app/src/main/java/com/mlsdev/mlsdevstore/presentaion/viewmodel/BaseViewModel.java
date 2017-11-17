package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel{
    protected static final String LOG_TAG = "ViewModel.Log.Tag";
    public final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final ObservableBoolean technicalErrorOccurred = new ObservableBoolean();
    public final ObservableBoolean networkErrorOccurred = new ObservableBoolean();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void onTechnicalErrorOccurred() {
        technicalErrorOccurred.set(true);
    }

    public void onNetworkErrorOccurred() {
        networkErrorOccurred.set(true);
    }

    public void onAuthorizationErrorOccurred() {

    }
}
