package com.mlsdev.mlsdevstore.presentaion.cart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.FragmentCartBinding;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return binding.getRoot();
    }
}
