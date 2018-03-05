package com.mlsdev.mlsdevstore.presentaion.checkout;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityCheckoutBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;

public class CheckoutActivity extends BaseActivity {
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel checkoutViewModel;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CheckoutActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        init();
    }

    private void init() {
        checkoutViewModel = ViewModelProviders.of(this, viewModelFactory).get(CheckoutViewModel.class);
        binding.setViewModel(checkoutViewModel);
        errorInViewHandler.subscribeAllErrorCallbacks(checkoutViewModel, false);
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }
}
