package com.mlsdev.mlsdevstore.presentaion.product;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class ProductDetailsViewModel extends BaseViewModel {
    private Item item;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();

    @Inject
    public ProductDetailsViewModel() {

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
    }

}
