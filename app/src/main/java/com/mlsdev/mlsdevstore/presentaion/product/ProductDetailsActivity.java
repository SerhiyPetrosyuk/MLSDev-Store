package com.mlsdev.mlsdevstore.presentaion.product;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.databinding.ActivityProductDetailsBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;

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
        viewModel.descriptionIsDisplayed.addOnPropertyChangedCallback(descriptionCallBack);
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }

    private Observable.OnPropertyChangedCallback descriptionCallBack = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof CustomObservableBoolean && ((CustomObservableBoolean)observable).get()) {
                DescriptionBottomSheetFragment bottomSheet = new DescriptionBottomSheetFragment();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        }
    };

    @Override
    protected void onDestroy() {
        viewModel.descriptionIsDisplayed.removeOnPropertyChangedCallback(descriptionCallBack);
        super.onDestroy();
    }
}
