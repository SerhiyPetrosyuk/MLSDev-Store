package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingOption implements Parcelable{

    @ShippingType
    private String shippingCostType;
    private Price shippingCost;

    public ShippingOption() {
    }

    public @interface ShippingType {
        String FIXED_PRICE = "FIXED_PRICE";
        String CALCULATED = "CALCULATED";
    }

    public String getShippingCostType() {
        return shippingCostType;
    }

    public Price getShippingCost() {
        return shippingCost;
    }

    public void setShippingCostType(String shippingCostType) {
        this.shippingCostType = shippingCostType;
    }

    public void setShippingCost(Price shippingCost) {
        this.shippingCost = shippingCost;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shippingCostType);
        dest.writeParcelable(this.shippingCost, flags);
    }

    protected ShippingOption(Parcel in) {
        this.shippingCostType = in.readString();
        this.shippingCost = in.readParcelable(Price.class.getClassLoader());
    }

    public static final Creator<ShippingOption> CREATOR = new Creator<ShippingOption>() {
        @Override
        public ShippingOption createFromParcel(Parcel source) {
            return new ShippingOption(source);
        }

        @Override
        public ShippingOption[] newArray(int size) {
            return new ShippingOption[size];
        }
    };
    //endregion
}
