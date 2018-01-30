package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.mlsdev.mlsdevstore.data.model.seller.Seller;

import java.util.ArrayList;
import java.util.List;

public class Item implements Parcelable, ListItem {
    private String itemId;
    private String title;
    private String itemHref;
    @Condition
    private String condition;
    private String conditionId;
    private boolean adultOnly;
    private Image image;
    private Price price;
    private Price currentBidPrice;
    private Seller seller;
    private List<String> buyingOptions = new ArrayList<>();
    private List<ShippingOption> shippingOptions = new ArrayList<>();
    private ItemLocation itemLocation;
    private List<Image> additionalImages = new ArrayList<>();

    @Override
    public String getId() {
        return itemId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImage() {
        String imageUrl = image != null ? image.getImageUrl() : null;

        if (imageUrl == null && !additionalImages.isEmpty())
            imageUrl = additionalImages.get(0).getImageUrl();

        return imageUrl;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public Parcelable getParcelable() {
        return this;
    }

    public @interface Condition {
        String New = "New";
        String Used = "Used";
    }

    public Item() {
    }

    public Item(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.title);
        dest.writeString(this.itemHref);
        dest.writeString(this.condition);
        dest.writeString(this.conditionId);
        dest.writeByte(this.adultOnly ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.image, flags);
        dest.writeParcelable(this.price, flags);
        dest.writeParcelable(this.currentBidPrice, flags);
        dest.writeParcelable(this.seller, flags);
        dest.writeStringList(this.buyingOptions);
        dest.writeTypedList(this.shippingOptions);
        dest.writeParcelable(this.itemLocation, flags);
    }

    protected Item(Parcel in) {
        this.itemId = in.readString();
        this.title = in.readString();
        this.itemHref = in.readString();
        this.condition = in.readString();
        this.conditionId = in.readString();
        this.adultOnly = in.readByte() != 0;
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.price = in.readParcelable(Price.class.getClassLoader());
        this.currentBidPrice = in.readParcelable(Price.class.getClassLoader());
        this.seller = in.readParcelable(Seller.class.getClassLoader());
        this.buyingOptions = in.createStringArrayList();
        this.shippingOptions = in.createTypedArrayList(ShippingOption.CREATOR);
        this.itemLocation = in.readParcelable(ItemLocation.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    //endregion
}
