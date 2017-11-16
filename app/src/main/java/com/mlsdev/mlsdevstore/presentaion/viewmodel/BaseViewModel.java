package com.mlsdev.mlsdevstore.presentaion.viewmodel;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel{
    protected static final String LOG_TAG = "ViewModel.Log.Tag";
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

}
