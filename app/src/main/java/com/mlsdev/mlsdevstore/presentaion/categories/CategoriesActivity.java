package com.mlsdev.mlsdevstore.presentaion.categories;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.databinding.ActivityCategoriesBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.store.CategoriesAdapter;

import java.util.List;

import javax.inject.Inject;

public class CategoriesActivity extends BaseActivity {
    private ActivityCategoriesBinding binding;
    private CategoriesViewModel viewModel;

    @Inject
    CategoriesAdapter categoriesAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, CategoriesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_categories);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel.class);
        viewModel.listObservableField.addOnPropertyChangedCallback(onCategoriesListChangedListener);
        binding.setViewModel(viewModel);
        initRecyclerView();
        initToolbar(binding.toolbar);
        displayBackArrow(true);
        errorInViewHandler.subscribeAllErrorCallbacks(viewModel, true);
        viewModel.getRootCategories();
    }

    @Override
    protected void onDestroy() {
        viewModel.listObservableField.removeOnPropertyChangedCallback(onCategoriesListChangedListener);
        super.onDestroy();
    }

    private void initRecyclerView() {
        binding.rvCategories.setAdapter(categoriesAdapter);
    }

    private Observable.OnPropertyChangedCallback onCategoriesListChangedListener = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof ObservableField) {
                categoriesAdapter.setData(((ObservableField<List<CategoryTreeNode>>) observable).get());
                binding.rvCategories.notifyDataSetChanged();
            }
        }
    };
}
