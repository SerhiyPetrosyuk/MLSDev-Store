package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcelable;

public interface ListItem {
    String getId();

    String getTitle();

    String getImage();

    Price getPrice();

    @Item.Condition
    String getCondition();

    Parcelable getParcelable();
}
