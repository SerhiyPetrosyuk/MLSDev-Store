package com.mlsdev.mlsdevstore.data.model.item;


import android.os.Parcel;
import android.os.Parcelable;

public class ItemLocation implements Parcelable{
    private String country;

    public ItemLocation() {
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
    }

    protected ItemLocation(Parcel in) {
        this.country = in.readString();
    }

    public static final Creator<ItemLocation> CREATOR = new Creator<ItemLocation>() {
        @Override
        public ItemLocation createFromParcel(Parcel source) {
            return new ItemLocation(source);
        }

        @Override
        public ItemLocation[] newArray(int size) {
            return new ItemLocation[size];
        }
    };
    //endregion
}
