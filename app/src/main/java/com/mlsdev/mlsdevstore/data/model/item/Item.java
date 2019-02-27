package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.mlsdev.mlsdevstore.data.local.database.dao.ProductTable;
import com.mlsdev.mlsdevstore.data.model.seller.Seller;

import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ProductTable.NAME)
public class Item implements Parcelable, ListItem {
    @PrimaryKey
    @ColumnInfo(name = ProductTable.COLUMN_ID)
    private String itemId;
    @ColumnInfo(name = ProductTable.COLUMN_TITLE)
    private String title;
    private String itemHref;
    @Condition
    private String condition;
    private String description;
    private String brand;
    private String size;
    private String gender;
    private String color;
    private String material;
    private boolean adultOnly;
    private Image image;
    private Price price;
    private Seller seller;
    private List<Image> additionalImages = new ArrayList<>();

    public List<Image> getAdditionalImages() {
        return additionalImages;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getGender() {
        return gender;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getId() {
        return itemId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImageUrl() {
        String imageUrl = image != null ? image.getImageUrl() : null;

        if (!additionalImages.isEmpty())
            imageUrl = additionalImages.get(0).getImageUrl();

        return imageUrl;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    public Seller getSeller() {
        return seller;
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

    public void setCondition(@Condition String condition) {
        this.condition = condition;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setPrice(Price price) {
        this.price = price;
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
        dest.writeByte(this.adultOnly ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.image, flags);
        dest.writeParcelable(this.price, flags);
        dest.writeParcelable(this.seller, flags);
    }

    protected Item(Parcel in) {
        this.itemId = in.readString();
        this.title = in.readString();
        this.itemHref = in.readString();
        this.condition = in.readString();
        this.adultOnly = in.readByte() != 0;
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.price = in.readParcelable(Price.class.getClassLoader());
        this.seller = in.readParcelable(Seller.class.getClassLoader());
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
