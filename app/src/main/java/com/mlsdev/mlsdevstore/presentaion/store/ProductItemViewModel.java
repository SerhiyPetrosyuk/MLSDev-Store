package com.mlsdev.mlsdevstore.presentaion.store;

import androidx.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;

public class ProductItemViewModel {
    private ListItem listItem;
    private Cart cart;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> priceFirstPart = new ObservableField<>();
    public final ObservableField<String> priceSecondPart = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final CustomObservableBoolean isNew = new CustomObservableBoolean();

    public void setItem(Cart cart, ListItem listItem) {
        this.cart = cart;
        this.listItem = listItem;

        String[] priceArray = String.valueOf(listItem.getPrice().getValue()).split("\\.");
        title.set(listItem.getTitle());
        if (priceArray.length > 0) {
            priceFirstPart.set(priceArray[0]);
            if (priceArray.length == 1)
                priceArray[1] = "00";

            if (priceArray[1].length() == 1)
                priceArray[1] = priceArray[1] + "0";

            priceSecondPart.set(priceArray[1]);
            currency.set(listItem.getPrice().getCurrency());
        }
        imageUrl.set(listItem.getImageUrl());
        isNew.set(listItem.getCondition() != null && listItem.getCondition().equals(Item.Condition.New));
    }

    public void onItemClick(View itemView) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtrasKeys.KEY_PRODUCT_DETAILS, listItem.getParcelable());
        ProductDetailsActivity.launch(itemView.getContext(), bundle);
    }

    public void removeFromCart() {
        cart.removeItem(listItem.getId());
    }

}
