package com.mlsdev.mlsdevstore.presentaion.fragment;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.ErrorInViewHandler;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment implements LifecycleOwner {

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
