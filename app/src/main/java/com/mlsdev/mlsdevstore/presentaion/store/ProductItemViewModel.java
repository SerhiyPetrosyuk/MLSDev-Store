package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.presentaion.product.ProductDetailsActivity;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys;

public class ProductItemViewModel {
    private ListItem listItem;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> priceFirstPart = new ObservableField<>();
    public final ObservableField<String> priceSecondPart = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final CustomObservableBoolean isNew = new CustomObservableBoolean();

    public void setItem(ListItem listItem) {
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
        isNew.set(listItem.getCondition().equals(Item.Condition.New));
    }

    public void onItemClick(View itemView) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtrasKeys.PRODUCT_DETAILS, listItem.getParcelable());
        ProductDetailsActivity.launch(itemView.getContext(), bundle);
    }

}
