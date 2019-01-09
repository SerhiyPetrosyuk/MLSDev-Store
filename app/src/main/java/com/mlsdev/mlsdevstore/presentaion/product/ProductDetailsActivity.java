package com.mlsdev.mlsdevstore.presentaion.product;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.Image;
import com.mlsdev.mlsdevstore.databinding.ActivityProductDetailsBinding;
import com.mlsdev.mlsdevstore.presentaion.BaseActivity;
import com.mlsdev.mlsdevstore.presentaion.adapter.ProductGalleryAdapter;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;

import java.util.List;

import javax.inject.Inject;

public class ProductDetailsActivity extends BaseActivity {
    private ActivityProductDetailsBinding binding;
    private ProductDetailsViewModel viewModel;

    @Inject
    ProductGalleryAdapter galleryAdapter;

    public ProductDetailsViewModel getViewModel() {
        return viewModel;
    }

    public static void launch(Context context, Bundle bundleProductData) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtras(bundleProductData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ProductDetailsViewModel.class);
        getErrorInViewHandler().subscribeAllErrorCallbacks(viewModel, true);
        binding.setViewModel(viewModel);
        viewModel.setProductDetailsData(getIntent().getExtras());
        viewModel.descriptionIsDisplayed.addOnPropertyChangedCallback(descriptionCallBack);
        viewModel.imageList.addOnPropertyChangedCallback(galleryImagesLoadedCallback);
        binding.productImagesGallery.setAdapter(galleryAdapter);
        getLifecycle().addObserver(viewModel);
        initToolbar(binding.toolbar);
        displayBackArrow(true);
    }

    private Observable.OnPropertyChangedCallback descriptionCallBack = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (observable instanceof CustomObservableBoolean && ((CustomObservableBoolean) observable).get()) {
                Bundle data = new Bundle();
                data.putString(ExtrasKeys.KEY_PRODUCT_DESCRIPTION, viewModel.description.get());
                DescriptionBottomSheetFragment bottomSheet = new DescriptionBottomSheetFragment();
                bottomSheet.setArguments(data);
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        }
    };

    private Observable.OnPropertyChangedCallback galleryImagesLoadedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (ObservableField.class.isAssignableFrom(observable.getClass())) {
                ObservableField<List<Image>> listObservableField = (ObservableField<List<Image>>) observable;
                galleryAdapter.setImages(listObservableField.get());
            }
        }
    };

    @Override
    protected void onDestroy() {
        viewModel.descriptionIsDisplayed.removeOnPropertyChangedCallback(descriptionCallBack);
        viewModel.imageList.removeOnPropertyChangedCallback(galleryImagesLoadedCallback);
        super.onDestroy();
    }
}
