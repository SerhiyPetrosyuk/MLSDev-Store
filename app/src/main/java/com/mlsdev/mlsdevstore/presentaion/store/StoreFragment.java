package com.mlsdev.mlsdevstore.presentaion.store;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.FragmentStoreBinding;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;

import javax.inject.Inject;

public class StoreFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentStoreBinding binding;
    private StoreViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ProductsAdapter productsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel.class);
        viewModel.searchResult.addOnPropertyChangedCallback(onProductsChangedCallback);
        binding.setViewModel(viewModel);
        binding.refreshLayout.setOnRefreshListener(this);
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getProducts();
    }

    private Observable.OnPropertyChangedCallback onProductsChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof ObservableField){
                ObservableField<SearchResult> results = (ObservableField<SearchResult>) observable;
                productsAdapter.setData(results.get());
                binding.rvProducts.notifyDataSetChanged();
            }
        }
    };

    private void initRecyclerView() {
        binding.rvProducts.setAdapter(productsAdapter);
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }
}
