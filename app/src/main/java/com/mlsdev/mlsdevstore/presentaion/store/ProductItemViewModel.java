package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean;

public class ProductItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> priceFirstPart = new ObservableField<>();
    public final ObservableField<String> priceSecondPart = new ObservableField<>();
    public final ObservableField<String> currency = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final CustomObservableBoolean isNew = new CustomObservableBoolean();

    public void setItem(ListItem listItem) {
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
        imageUrl.set(listItem.getImage());
        isNew.set(listItem.getCondition().equals(Item.Condition.New));
    }

}
