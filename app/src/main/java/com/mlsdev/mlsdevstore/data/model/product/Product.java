package com.mlsdev.mlsdevstore.data.model.product;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Product {

    @Element(name = "DomainName")
    private String domainName;

    @Element(name = "DetailsURL")
    private String detailURL;

    @Element(name = "DisplayStockPhotos")
    private boolean displayStockPhotos;

    @Element(name = "StockPhotoURL")
    private String stockPhotoURL;

    @Element(name = "Title")
    private String title;

    @Element(name = "ReviewCount")
    private int reviewCount;

    @ElementList(name = "ProductID", type = ProductID.class)
    private List<ProductID> productIDList;

    @Element(name = "ItemSpecifics", required = false)
    private ItemSpecifics itemSpecifics;

    public String getDomainName() {
        return domainName;
    }

    public String getDetailURL() {
        return detailURL;
    }

    public boolean isDisplayStockPhotos() {
        return displayStockPhotos;
    }

    public String getStockPhotoURL() {
        return stockPhotoURL;
    }

    public String getTitle() {
        return title;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public List<ProductID> getProductIDList() {
        return productIDList;
    }

    public ItemSpecifics getItemSpecifics() {
        return itemSpecifics;
    }
}
