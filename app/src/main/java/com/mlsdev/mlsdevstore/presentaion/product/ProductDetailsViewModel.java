package com.mlsdev.mlsdevstore.presentaion.product;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Context;
import androidx.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.Image;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.remote.BaseObserver;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;
import com.mlsdev.mlsdevstore.presentaion.utils.Utils;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class ProductDetailsViewModel extends BaseViewModel
        implements Cart.OnMaxItemsReachedListener, Cart.OnItemAddedListener {
    private Item item;
    private Cart cart;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();
    public final ObservableField<String> feedbackScore = new ObservableField<>("0.0");
    public final ObservableField<String> feedbackPercent = new ObservableField<>("0.0");
    public final CustomObservableBoolean descriptionIsDisplayed = new CustomObservableBoolean();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> condition = new ObservableField<>();
    public final ObservableField<String> brand = new ObservableField<>();
    public final ObservableField<String> size = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> color = new ObservableField<>();
    public final ObservableField<String> material = new ObservableField<>();
    public final ObservableField<List<Image>> imageList = new ObservableField<>();

    @Inject
    public ProductDetailsViewModel(Context context, DataSource dataSource, Utils utils, Cart cart) {
        this.context = context;
        this.dataSource = dataSource;
        this.utils = utils;
        this.cart = cart;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        cart.addOnMaxItemsReachedListener(this);
        cart.addOnItemAddedListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        cart.removeOnMaxItemsReachedListener(this);
        cart.removeOnItemAddedListener(this);
    }

    public void setProductDetailsData(Bundle productDetailsData) {
        if (productDetailsData == null)
            return;

        item = productDetailsData.getParcelable(ExtrasKeys.PRODUCT_DETAILS);

        if (item == null)
            return;

        title.set(item.getTitle());
        imageUrl.set(item.getImageUrl());
        price.set(String.valueOf(item.getPrice().getValue()));
        currency.set(item.getPrice().getCurrency());
        condition.set(item.getCondition());
        retrieveDetailedInfo(item.getId());

    }

    public void retrieveDetailedInfo(String itemId) {

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
                        brand.set(data.getBrand());
                        size.set(data.getSize());
                        gender.set(data.getGender());
                        color.set(data.getColor());
                        material.set(data.getMaterial());
                        feedbackScore.set(String.valueOf(data.getSeller().getFeedbackScore()));
                        feedbackPercent.set(data.getSeller().getFeedbackPercentage());
                        imageUrl.set(data.getImageUrl());
                        imageUrl.notifyChange();

                        List<Image> images = data.getAdditionalImages();
                        if (images.isEmpty())
                            images.add(data.getImage());

                        imageList.set(data.getAdditionalImages());
                        imageList.notifyChange();
                    }
                });

    }

    public void onAddToCartClicked(View button) {
        cart.addItem(item);
    }

    public void onDescriptionClick() {
        descriptionIsDisplayed.set(true);
    }

    @Override
    public void onMaxItemsReached() {
        Toast.makeText(context, R.string.message_item_count_restriction, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAdded(Item item) {
        Toast.makeText(context, R.string.message_item_added, Toast.LENGTH_SHORT).show();
    }
}
