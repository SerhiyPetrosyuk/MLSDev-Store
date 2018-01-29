package com.mlsdev.mlsdevstore.presentaion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.presentaion.ErrorInViewHandler;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    @Inject
    protected ErrorInViewHandler errorInViewHandler;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        errorInViewHandler.setContext(getActivity());
    }
}
