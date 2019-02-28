package com.mlsdev.mlsdevstore.data.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.mlsdev.mlsdevstore.data.local.database.tables.ProductsTable;
import com.mlsdev.mlsdevstore.data.model.seller.Seller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ProductsTable.NAME)
public class Item implements Parcelable, ListItem {
    @PrimaryKey
    @ColumnInfo(name = ProductsTable.COLUMN_ID)
    @NonNull
    private String itemId = "id";
    @ColumnInfo(name = ProductsTable.COLUMN_TITLE)
    private String title;
    @Ignore
    private String itemHref;
    @Condition
    private String condition;
    @Ignore
    private String description;
    @Ignore
    private String brand;
    @Ignore
    private String size;
    @Ignore
    private String gender;
    @Ignore
    private String color;
    @Ignore
    private String material;
    @Ignore
    private boolean adultOnly;
    @Embedded
    private Image image;
    @Embedded
    private Price price;
    @Ignore
    private Seller seller;
    @Ignore
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

    @NonNull
    public String getItemId() {
        return itemId;
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

    public void setImage(Image image) {
        this.image = image;
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
