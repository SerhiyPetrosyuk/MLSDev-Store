package com.mlsdev.mlsdevstore.presentaion.cart;

import android.arch.lifecycle.ViewModelProviders;
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
    private CartViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CartViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.navigation_title_cart);
    }
}
