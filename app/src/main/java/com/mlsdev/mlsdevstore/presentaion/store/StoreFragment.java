package com.mlsdev.mlsdevstore.presentaion.store;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.category.Category;
import com.mlsdev.mlsdevstore.databinding.FragmentStoreBinding;
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

public class StoreFragment extends BaseFragment {
    private FragmentStoreBinding binding;
    private StoreViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel.class);
        viewModel.categories.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                for (Category category : ((ObservableField<List<Category>>) observable).get())
                    Log.d("Category", category.getCategoryName());
            }
        });

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getCategories();
    }
}
