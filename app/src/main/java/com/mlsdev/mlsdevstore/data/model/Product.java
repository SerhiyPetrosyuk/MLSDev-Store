package com.mlsdev.mlsdevstore.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

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

    @Element(name = "ItemSpecifics")
    private ItemSpecifics itemSpecifics;

}
