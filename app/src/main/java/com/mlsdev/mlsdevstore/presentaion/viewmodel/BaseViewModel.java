package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {
    protected static final String LOG_TAG = "ViewModel.Log.Tag";
    public final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final ObservableBoolean technicalErrorOccurred = new ObservableBoolean();
    public final ObservableBoolean networkErrorOccurred = new ObservableBoolean();
    public final ObservableBoolean commonErrorOccurred = new ObservableBoolean();
    public final ObservableBoolean authErrorOccurred = new ObservableBoolean();
    public final ObservableBoolean isRefreshing = new ObservableBoolean();
    protected DataSource dataSource;
    protected Utils utils;

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void onTechnicalErrorOccurred() {
        technicalErrorOccurred.set(true);
        setIsRefreshing(false);
    }

    public void onNetworkErrorOccurred() {
        networkErrorOccurred.set(true);
        setIsRefreshing(false);
    }

    public void onCommonErrorOccurred() {
        commonErrorOccurred.set(true);
        setIsRefreshing(false);
    }

    public void onAuthorizationErrorOccurred() {
        authErrorOccurred.set(true);
        setIsRefreshing(false);
    }

    protected void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing.set(isRefreshing);
        this.isRefreshing.notifyChange();
    }
}
