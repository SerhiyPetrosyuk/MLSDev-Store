package com.mlsdev.mlsdevstore.presentaion.product;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityProductDetailsBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;

public class ProductDetailsActivity extends BaseActivity {
    private ActivityProductDetailsBinding binding;
    private ProductDetailsViewModel viewModel;

    public static void launch(Context context, Bundle bundleProductData) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtras(bundleProductData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailsViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.setProductDetailsData(getIntent().getExtras());
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }
}
