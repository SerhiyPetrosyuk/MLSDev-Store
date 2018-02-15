package com.mlsdev.mlsdevstore.presentaion.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.ErrorInViewHandler;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    @Inject
    protected ErrorInViewHandler errorInViewHandler;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        errorInViewHandler.setContext(getActivity());
    }

    protected void setTitle(@StringRes int title) {
        ((BaseActivity)getActivity()).setToolBarTitle(title);
    }
}
