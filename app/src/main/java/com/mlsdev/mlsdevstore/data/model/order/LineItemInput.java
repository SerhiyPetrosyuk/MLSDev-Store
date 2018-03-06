package com.mlsdev.mlsdevstore.data.model.order;

import com.google.gson.annotations.SerializedName;

public class LineItemInput {
    @SerializedName("itemId")
    private String itemId;
    @SerializedName("quantity")
    private int quantity;

    public LineItemInput() {
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
