package com.mlsdev.mlsdevstore.presentaion.product;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class ProductDetailsViewModel extends BaseViewModel {
    private Item item;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();
    public final ObservableField<String> feedbackScore = new ObservableField<>();
    public final ObservableField<String> feedbackPercent = new ObservableField<>();
    public final CustomObservableBoolean descriptionIsDisplayed = new CustomObservableBoolean();
    public final ObservableField<String> description = new ObservableField<>();

    @Inject
    public ProductDetailsViewModel(DataSource dataSource, Utils utils) {
        this.dataSource = dataSource;
        this.utils = utils;
    }

    public void setProductDetailsData(Bundle productDetailsData) {
        if (productDetailsData == null)
            return;

        item = productDetailsData.getParcelable(ExtrasKeys.PRODUCT_DETAILS);

        if (item == null)
            return;

        title.set(item.getTitle());
        imageUrl.set(item.getImage());
        price.set(String.valueOf(item.getPrice().getValue()));
        currency.set(item.getPrice().getCurrency());
        retrieveDetailedInfo(item.getId());

    }

    private void retrieveDetailedInfo(String itemId) {

        if (!utils.isNetworkAvailable()) {
            onNetworkErrorOccurred();
            return;
        }

        dataSource.getItem(itemId)
                .subscribe(new BaseObserver<Item>(this) {
                    @Override
                    public void onSuccess(Item data) {
                        super.onSuccess(data);
                        description.set(data.getDescription());
                        feedbackScore.set(String.valueOf((int) item.getSeller().getFeedbackScore()));
                        feedbackPercent.set(item.getSeller().getFeedbackPercentage());
                    }
                });

    }

    public void onDescriptionClick() {
        descriptionIsDisplayed.set(true);
    }

}
