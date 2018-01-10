package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {
    private double value;
    private String currency;

    public Price() {
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.value);
        dest.writeString(this.currency);
    }

    protected Price(Parcel in) {
        this.value = in.readDouble();
        this.currency = in.readString();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
    //endregion

}
