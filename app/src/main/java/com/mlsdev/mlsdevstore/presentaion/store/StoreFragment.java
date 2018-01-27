package com.mlsdev.mlsdevstore.presentaion.store;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.FragmentStoreBinding;
import com.mlsdev.mlsdevstore.dependency_injection.Named;
import com.mlsdev.mlsdevstore.dependency_injection.module.ProductsAdapterModule;
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesActivity;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;

import javax.inject.Inject;

public class StoreFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentStoreBinding binding;
    private StoreViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    @Named(ProductsAdapterModule.Type.RandomProducts)
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
        viewModel.getProducts();
        return binding.getRoot();
    }

    private Observable.OnPropertyChangedCallback onProductsChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof ObservableField) {
                ObservableField<SearchResult> results = (ObservableField<SearchResult>) observable;
                boolean animate = binding.refreshLayout.isRefreshing() || productsAdapter.getItemCount() == 0;
                productsAdapter.setData(results.get());
                if (animate)
                    binding.rvProducts.notifyDataSetChanged();
                binding.refreshLayout.setRefreshing(false);
            }
        }
    };

    private void initRecyclerView() {
        binding.rvProducts.setAdapter(productsAdapter);
        ((GridLayoutManager) binding.rvProducts.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = productsAdapter.getItemViewType(position);

                if (viewType == RandomProductsAdapter.VIEW_TYPE_FOOTER || viewType == RandomProductsAdapter.VIEW_TYPE_HEADER)
                    return 2;
                else if (viewType == ProductsAdapter.VIEW_TYPE_ITEM)
                    return 1;
                else
                    return -1;
            }
        });

        productsAdapter.setOnClickListeners(
                view -> {
                }, // TODO: 26.01.18 select the account tab
                view -> CategoriesActivity.launch(getContext()),
                view -> viewModel.loadMoreItemsFromRandomCategory()
        );
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }
}
