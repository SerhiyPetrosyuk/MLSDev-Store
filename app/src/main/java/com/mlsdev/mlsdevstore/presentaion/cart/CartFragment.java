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
import com.mlsdev.mlsdevstore.dependency_injection.NamedAdapter;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter;

import javax.inject.Inject;
import javax.inject.Named;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding binding;
    private CartViewModel viewModel;

    @Inject
    @Named(NamedAdapter.CART_ITEMS_ADAPTER)
    ProductsAdapter itemsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CartViewModel.class);
        binding.setViewModel(viewModel);
        getLifecycle().addObserver(viewModel);
        getLifecycle().addObserver(itemsAdapter);

        binding.itemsRecycler.setAdapter(itemsAdapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        getLifecycle().removeObserver(viewModel);
        getLifecycle().removeObserver(itemsAdapter);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.navigation_title_cart);
    }
}
