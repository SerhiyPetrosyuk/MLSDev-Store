package com.mlsdev.mlsdevstore.presentaion.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityProductDetailsBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;

public class ProductDetailsActivity extends BaseActivity {
    private ActivityProductDetailsBinding binding;

    public static void launch(Context context, Bundle bundleProductData) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtras(bundleProductData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }
}
