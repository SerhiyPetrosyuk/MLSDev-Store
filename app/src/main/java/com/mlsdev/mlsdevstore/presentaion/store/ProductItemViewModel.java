package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.model.item.ListItem;

public class ProductItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public void setItem(ListItem listItem) {
        title.set(listItem.getTitle());
        price.set(listItem.getPrice());
        imageUrl.set(listItem.getImage());
    }

}
