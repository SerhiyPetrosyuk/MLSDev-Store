package com.mlsdev.mlsdevstore.data.model.item;

public interface ListItem {
    String getId();

    String getTitle();

    String getImage();

    Price getPrice();

    @Item.Condition
    String getCondition();
}
